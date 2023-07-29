package com.a5lab.axion.domain.technology_blip;

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
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyRepository;

class TechnologyBlipServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private RadarTypeRepository radarTypeRepository;
  @Autowired
  private RadarRepository radarRepository;
  @Autowired
  private RingRepository ringRepository;
  @Autowired
  private SegmentRepository segmentRepository;
  @Autowired
  private TechnologyRepository technologyRepository;
  @Autowired
  private TechnologyBlipService technologyBlipService;
  @Autowired
  private TechnologyBlipRepository technologyBlipRepository;

  @Test
  @Transactional
  void shouldFindAllRingWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My first radar");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    final Radar radar1 = new Radar();
    radar1.setTitle("My second radar");
    radar1.setRadarType(radarType);
    radar1.setDescription("My radar description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("Color");
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My description");
    segment.setPosition(1);
    segmentRepository.saveAndFlush(segment);

    final Technology technology = new Technology();
    technology.setTitle("My first technology title");
    technology.setWebsite("My technology website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);
    technologyRepository.saveAndFlush(technology);

    final Technology technology1 = new Technology();
    technology1.setTitle("My second technology title");
    technology1.setWebsite("My technology website");
    technology1.setDescription("My technology description");
    technology1.setMoved(1);
    technology1.setActive(true);
    technologyRepository.saveAndFlush(technology1);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setSegment(segment);
    technologyBlip.setTechnology(technology);

    final TechnologyBlip technologyBlip1 = new TechnologyBlip();
    technologyBlip1.setRadar(radar1);
    technologyBlip1.setRing(ring);
    technologyBlip1.setSegment(segment);
    technologyBlip1.setTechnology(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(technologyBlip, technologyBlip1);
    for (TechnologyBlip technologyBlipAll : technologyBlipList) {
      technologyBlipRepository.save(technologyBlipAll);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "radar.title"),
        new Sort.Order(Sort.Direction.ASC, "technology.title"),
        new Sort.Order(Sort.Direction.ASC, "segment.title"),
        new Sort.Order(Sort.Direction.ASC, "ring.title")));
    Page<TechnologyBlipDto> technologyBlipDtoPage = technologyBlipService.findAll(null, pageable);
    Assertions.assertEquals(10, technologyBlipDtoPage.getSize());
    Assertions.assertEquals(0, technologyBlipDtoPage.getNumber());
    Assertions.assertEquals(1, technologyBlipDtoPage.getTotalPages());
    Assertions.assertEquals(2, technologyBlipDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllTechnologyBlipsWithBlankTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My first radar");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    final Radar radar1 = new Radar();
    radar1.setTitle("My second radar");
    radar1.setRadarType(radarType);
    radar1.setDescription("My radar description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("Color");
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My description");
    segment.setPosition(1);
    segmentRepository.saveAndFlush(segment);

    final Technology technology = new Technology();
    technology.setTitle("My first technology title");
    technology.setWebsite("My technology website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);
    technologyRepository.saveAndFlush(technology);

    final Technology technology1 = new Technology();
    technology1.setTitle("My second technology title");
    technology1.setWebsite("My technology website");
    technology1.setDescription("My technology description");
    technology1.setMoved(1);
    technology1.setActive(true);
    technologyRepository.saveAndFlush(technology1);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setSegment(segment);
    technologyBlip.setTechnology(technology);

    final TechnologyBlip technologyBlip1 = new TechnologyBlip();
    technologyBlip1.setRadar(radar1);
    technologyBlip1.setRing(ring);
    technologyBlip1.setSegment(segment);
    technologyBlip1.setTechnology(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(technologyBlip, technologyBlip1);
    for (TechnologyBlip technologyBlipAll : technologyBlipList) {
      technologyBlipRepository.save(technologyBlipAll);
    }

    TechnologyBlipFilter technologyBlipFilter = new TechnologyBlipFilter();
    technologyBlipFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "radar.title"),
        new Sort.Order(Sort.Direction.ASC, "technology.title"),
        new Sort.Order(Sort.Direction.ASC, "segment.title"),
        new Sort.Order(Sort.Direction.ASC, "ring.title")));
    Page<TechnologyBlipDto> technologyBlipDtoPage = technologyBlipService.findAll(null, pageable);
    Assertions.assertEquals(10, technologyBlipDtoPage.getSize());
    Assertions.assertEquals(0, technologyBlipDtoPage.getNumber());
    Assertions.assertEquals(1, technologyBlipDtoPage.getTotalPages());
    Assertions.assertEquals(2, technologyBlipDtoPage.getNumberOfElements());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRadarTitle(),
        technologyBlipList.iterator().next().getRadar().getTitle());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRadarId(),
        technologyBlipList.iterator().next().getRadar().getId());
  }

  /*
  @Test
  @Transactional
  void shouldFindAllRingWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setTitle("My first radar");
    radar.setRadarType(radarType);
    radar.setDescription("My radar description");
    radar.setPrimary(false);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    final Radar radar1 = new Radar();
    radar1.setTitle("My second radar");
    radar1.setRadarType(radarType);
    radar1.setDescription("My radar description");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring();
    ring.setRadar(radar);
    ring.setTitle("TRIAL");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("Color");
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My description");
    segment.setPosition(1);
    segmentRepository.saveAndFlush(segment);

    final Technology technology = new Technology();
    technology.setTitle("My first technology title");
    technology.setWebsite("My technology website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);
    technologyRepository.saveAndFlush(technology);

    final Technology technology1 = new Technology();
    technology1.setTitle("My second technology title");
    technology1.setWebsite("My technology website");
    technology1.setDescription("My technology description");
    technology1.setMoved(1);
    technology1.setActive(true);
    technologyRepository.saveAndFlush(technology1);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setSegment(segment);
    technologyBlip.setTechnology(technology);

    final TechnologyBlip technologyBlip1 = new TechnologyBlip();
    technologyBlip1.setRadar(radar1);
    technologyBlip1.setRing(ring);
    technologyBlip1.setSegment(segment);
    technologyBlip1.setTechnology(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(technologyBlip, technologyBlip1);
    for (TechnologyBlip technologyBlipAll : technologyBlipList) {
      technologyBlipRepository.save(technologyBlipAll);
    }
    TechnologyBlipFilter technologyBlipFilter = new TechnologyBlipFilter();
    technologyBlipFilter.setTitle(technologyBlipList.iterator().next().getRadar().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "radar.title"),
        new Sort.Order(Sort.Direction.ASC, "technology.title"),
        new Sort.Order(Sort.Direction.ASC, "segment.title"),
        new Sort.Order(Sort.Direction.ASC, "ring.title")));
    Page<TechnologyBlipDto> technologyBlipDtoPage = technologyBlipService.findAll(null, pageable);
    System.out.println(technologyBlipDtoPage.iterator().next().getRadarTitle());
    System.out.println(technologyBlipList.iterator().next().getRadar().getTitle());
    System.out.println(technologyBlipDtoPage.getNumberOfElements());

    Assertions.assertEquals(10, technologyBlipDtoPage.getSize());
    Assertions.assertEquals(0, technologyBlipDtoPage.getNumber());
    Assertions.assertEquals(1, technologyBlipDtoPage.getTotalPages());
    Assertions.assertEquals(1, technologyBlipDtoPage.getNumberOfElements());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRadarTitle(),
        technologyBlipList.iterator().next().getRadar().getTitle());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRadarId(),
        technologyBlipList.iterator().next().getRadar().getId());
  }
   */
}
