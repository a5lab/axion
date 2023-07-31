package com.a5lab.axion.domain.ring;

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
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class RingServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private RadarTypeRepository radarTypeRepository;
  @Autowired
  private RadarRepository radarRepository;
  @Autowired
  private RingService ringService;
  @Autowired
  private RingRepository ringRepository;

  @Test
  @Transactional
  void shouldFindAllRingsWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My radar title");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    List<Ring> ringList = List.of(
        new Ring(null, radar, "TRIAL", "Description", 0, "Color", null),
        new Ring(null, radar, "ADOPT", "New description", 1, "Color", null)
    );
    for (Ring ring : ringList) {
      ringRepository.save(ring);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RingDto> ringDtoPage = ringService.findAll(null, pageable);
    Assertions.assertEquals(10, ringDtoPage.getSize());
    Assertions.assertEquals(0, ringDtoPage.getNumber());
    Assertions.assertEquals(1, ringDtoPage.getTotalPages());
    Assertions.assertEquals(2, ringDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllRingsWithBlankTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My radar title");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    List<Ring> ringList = List.of(
        new Ring(null, radar, "TRIAL", "Description", 0, "Color", null),
        new Ring(null, radar, "ADOPT", "New description", 1, "Color", null)
    );
    for (Ring ring : ringList) {
      ringRepository.save(ring);
    }

    RingFilter ringFilter = new RingFilter();
    ringFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RingDto> ringDtoPage = ringService.findAll(ringFilter, pageable);
    Assertions.assertEquals(10, ringDtoPage.getSize());
    Assertions.assertEquals(0, ringDtoPage.getNumber());
    Assertions.assertEquals(1, ringDtoPage.getTotalPages());
    Assertions.assertEquals(2, ringDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllRingsWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My radar title");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    List<Ring> ringList = List.of(
        new Ring(null, radar, "TRIAL", "Description", 0, "Color", null),
        new Ring(null, radar, "ADOPT", "New description", 1, "Color", null)
    );
    for (Ring ring : ringList) {
      ringRepository.save(ring);
    }

    RingFilter ringFilter = new RingFilter();
    ringFilter.setTitle(ringList.iterator().next().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<RingDto> ringDtoPage = ringService.findAll(ringFilter, pageable);
    Assertions.assertEquals(10, ringDtoPage.getSize());
    Assertions.assertEquals(0, ringDtoPage.getNumber());
    Assertions.assertEquals(1, ringDtoPage.getTotalPages());
    Assertions.assertEquals(1, ringDtoPage.getNumberOfElements());
    Assertions.assertNotNull(ringDtoPage.iterator().next().getId());
    Assertions.assertEquals(ringDtoPage.iterator().next().getTitle(), ringList.iterator().next().getTitle());
    Assertions.assertEquals(ringDtoPage.iterator().next().getDescription(),
        ringList.iterator().next().getDescription());
  }
}
