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
  void shouldFindAllRadarWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(false);
    radar.setActive(false);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
  void shouldFindAllRadarWithBlankTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(false);
    radar.setActive(false);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
  void shouldFindAllRadarWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(false);
    radar.setActive(false);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
  void shouldFindAllRadarWithPrimaryFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(true);
    radar.setActive(false);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
  void shouldFindAllRadarWithActiveFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(false);
    radar.setActive(true);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(true);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
  void shouldFindAllRadarWithPrimaryAndActiveFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar");
    radar.setDescription("My description");
    radar.setPrimary(true);
    radar.setActive(true);

    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new radar");
    radar1.setDescription("My new description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarType.setRadarList(List.of(radar, radar1));
    List<Radar> radarList = List.of(radar, radar1);
    for (Radar radarAll : radarList) {
      radarRepository.save(radarAll);
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
