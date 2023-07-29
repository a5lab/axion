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
  void shouldFindAllRingWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My ring type");
    radar.setRadarType(radarType);
    radar.setDescription("My ring type description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My description");
    ring.setPosition(0);
    ring.setColor("Color");

    final Ring ring1 = new Ring();
    ring1.setRadar(radar);
    ring1.setTitle("ADOPT");
    ring1.setDescription("My new description");
    ring1.setPosition(1);
    ring1.setColor("Color");
    radar.setRingList(List.of(ring, ring1));
    List<Ring> ringList = List.of(ring, ring1);
    for (Ring ringAll : ringList) {
      ringRepository.save(ringAll);
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
  void shouldFindAllRingWithBlankTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My ring type");
    radar.setRadarType(radarType);
    radar.setDescription("My ring type description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My description");
    ring.setPosition(0);
    ring.setColor("Color");

    final Ring ring1 = new Ring();
    ring1.setRadar(radar);
    ring1.setTitle("ADOPT");
    ring1.setDescription("My new description");
    ring1.setPosition(1);
    ring1.setColor("Color");
    radar.setRingList(List.of(ring, ring1));
    List<Ring> ringList = List.of(ring, ring1);
    for (Ring ringAll : ringList) {
      ringRepository.save(ringAll);
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
  void shouldFindAllRingWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My ring type");
    radar.setRadarType(radarType);
    radar.setDescription("My ring type description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My description");
    ring.setPosition(0);
    ring.setColor("Color");

    final Ring ring1 = new Ring();
    ring1.setRadar(radar);
    ring1.setTitle("ADOPT");
    ring1.setDescription("My new description");
    ring1.setPosition(1);
    ring1.setColor("Color");
    radar.setRingList(List.of(ring, ring1));
    List<Ring> ringList = List.of(ring, ring1);
    for (Ring ringAll : ringList) {
      ringRepository.save(ringAll);
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
