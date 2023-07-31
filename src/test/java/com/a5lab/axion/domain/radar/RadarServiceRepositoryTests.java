package com.a5lab.axion.domain.radar;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class RadarServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private RadarTypeRepository radarTypeRepository;
  @Autowired
  private RadarService radarService;
  @Autowired
  private RadarRepository radarRepository;

  @Test
  @Transactional
  void shouldFindAllRadarsWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", false, false, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", false, false, null, null, null)
    );
    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(null, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(2, radarDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllRadarsWithBlankTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", false, false, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", false, false, null, null, null)
    );
    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(2, radarDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllRadarsWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", false, false, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", false, false, null, null, null)
    );    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setTitle(radarList.iterator().next().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(1, radarDtoPage.getNumberOfElements());
    Assertions.assertNotNull(radarDtoPage.iterator().next().getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radarList.iterator().next().getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(),
        radarList.iterator().next().getDescription());
  }

  @Test
  @Transactional
  void shouldFindAllRadarsWithPrimaryFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", true, false, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", false, false, null, null, null)
    );    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setFilterByPrimary(true);
    radarFilter.setPrimary(true);
    radarFilter.setFilterByActive(true);
    radarFilter.setActive(false);
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(1, radarDtoPage.getNumberOfElements());
    Assertions.assertNotNull(radarDtoPage.iterator().next().getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radarList.iterator().next().getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(),
        radarList.iterator().next().getDescription());
    Assertions.assertTrue(radarDtoPage.iterator().next().isPrimary());
  }

  @Test
  @Transactional
  void shouldFindAllRadarsWithActiveFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", false, true, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", true, false, null, null, null)
    );    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setFilterByPrimary(true);
    radarFilter.setPrimary(false);
    radarFilter.setFilterByActive(true);
    radarFilter.setActive(true);
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(1, radarDtoPage.getNumberOfElements());
    Assertions.assertNotNull(radarDtoPage.iterator().next().getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radarList.iterator().next().getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(),
        radarList.iterator().next().getDescription());
    Assertions.assertTrue(radarDtoPage.iterator().next().isActive());
  }

  @Test
  @Transactional
  void shouldFindAllRadarsWithPrimaryAndActiveFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    List<Radar> radarList = List.of(
        new Radar(null, radarType, "My first radar title", "Description", true, true, null, null, null),
        new Radar(null, radarType, "My second radar title", "New description", false, false, null, null, null)
    );    for (Radar radar : radarList) {
      radarRepository.save(radar);
    }

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setFilterByPrimary(true);
    radarFilter.setPrimary(true);
    radarFilter.setFilterByActive(true);
    radarFilter.setActive(true);
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(10, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(1, radarDtoPage.getNumberOfElements());
    Assertions.assertNotNull(radarDtoPage.iterator().next().getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radarList.iterator().next().getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(),
        radarList.iterator().next().getDescription());
    Assertions.assertTrue(radarDtoPage.iterator().next().isActive());
    Assertions.assertTrue(radarDtoPage.iterator().next().isPrimary());
  }
}
