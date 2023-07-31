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
  void shouldFindAllTechnologyBlipsWithNullFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar(null, radarType,
        "My first radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar);

    final Radar radar1 = new Radar(null, radarType,
        "My second radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring(null, radar, "ADOPT",
        "Description", 0, "Color", null);
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment(null, radar, "My segment title",
        "My segment description", 1, null);
    segmentRepository.saveAndFlush(segment);

    final Technology technology = new Technology(null, "My first technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology);

    final Technology technology1 = new Technology(null, "My second technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(
        new TechnologyBlip(null, radar, technology, segment, ring),
        new TechnologyBlip(null, radar1, technology1, segment, ring)
    );
    for (TechnologyBlip technologyBlip : technologyBlipList) {
      technologyBlipRepository.save(technologyBlip);
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
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    // Create radar for first blips
    final Radar radar = new Radar(null, radarType,
        "My first radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar);

    // Create radar for second blips
    final Radar radar1 = new Radar(null, radarType,
        "My second radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring(null, radar, "ADOPT",
        "Description", 0, "Color", null);
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment(null, radar, "My segment title",
        "My segment description", 1, null);
    segmentRepository.saveAndFlush(segment);

    // Create technology for first blips
    final Technology technology = new Technology(null, "My first technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology);

    // Create technology for second blips
    final Technology technology1 = new Technology(null, "My second technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(
        new TechnologyBlip(null, radar, technology, segment, ring),
        new TechnologyBlip(null, radar1, technology1, segment, ring)
    );
    for (TechnologyBlip technologyBlip : technologyBlipList) {
      technologyBlipRepository.save(technologyBlip);
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
  void shouldFindAllTechnologyBlipsWithTitleFilter() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeRepository.saveAndFlush(radarType);

    // Create radar for first blips
    final Radar radar = new Radar(null, radarType,
        "My first radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar);

    // Create radar for second blips
    final Radar radar1 = new Radar(null, radarType,
        "My second radar title", "Description", true, true);
    radarRepository.saveAndFlush(radar1);

    final Ring ring = new Ring(null, radar, "ADOPT",
        "Description", 0, "Color", null);
    ringRepository.saveAndFlush(ring);

    final Segment segment = new Segment(null, radar, "My segment title",
        "My segment description", 1, null);
    segmentRepository.saveAndFlush(segment);

    // Create technology for first blips
    final Technology technology = new Technology(null, "My first technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology);

    // Create technology for second blips
    final Technology technology1 = new Technology(null, "My second technology title",
        "Website", "Description", 1, true);
    technologyRepository.saveAndFlush(technology1);

    List<TechnologyBlip> technologyBlipList = List.of(
        new TechnologyBlip(null, radar, technology, segment, ring),
        new TechnologyBlip(null, radar1, technology1, segment, ring)
    );
    for (TechnologyBlip technologyBlip : technologyBlipList) {
      technologyBlipRepository.save(technologyBlip);
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
