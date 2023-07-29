package com.a5lab.axion.domain.segment;

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

class SegmentServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private RadarTypeRepository radarTypeRepository;
  @Autowired
  private RadarRepository radarRepository;
  @Autowired
  private SegmentService segmentService;
  @Autowired
  private SegmentRepository segmentRepository;

  @Test
  @Transactional
  void shouldFindAllSegmentsWithNullFilter() {
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
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);

    final Segment segment1 = new Segment();
    segment1.setRadar(radar);
    segment1.setTitle("My new segment title");
    segment1.setDescription("My new segment description");
    segment1.setPosition(1);
    radar.setSegmentList(List.of(segment, segment1));
    List<Segment> segmentList = List.of(segment, segment1);
    for (Segment segmentAll : segmentList) {
      segmentRepository.save(segmentAll);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<SegmentDto> segmentDtoPage = segmentService.findAll(null, pageable);
    Assertions.assertEquals(10, segmentDtoPage.getSize());
    Assertions.assertEquals(0, segmentDtoPage.getNumber());
    Assertions.assertEquals(1, segmentDtoPage.getTotalPages());
    Assertions.assertEquals(2, segmentDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllSegmentsWithBlankTitleFilter() {
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
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);

    final Segment segment1 = new Segment();
    segment1.setRadar(radar);
    segment1.setTitle("My new segment title");
    segment1.setDescription("My new segment description");
    segment1.setPosition(1);
    radar.setSegmentList(List.of(segment, segment1));
    List<Segment> segmentList = List.of(segment, segment1);
    for (Segment segmentAll : segmentList) {
      segmentRepository.save(segmentAll);
    }

    SegmentFilter segmentFilter = new SegmentFilter();
    segmentFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<SegmentDto> segmentDtoPage = segmentService.findAll(segmentFilter, pageable);
    Assertions.assertEquals(10, segmentDtoPage.getSize());
    Assertions.assertEquals(0, segmentDtoPage.getNumber());
    Assertions.assertEquals(1, segmentDtoPage.getTotalPages());
    Assertions.assertEquals(2, segmentDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllSegmentsWithTitleFilter() {
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
    radarType.setRadarList(List.of(radar));
    radarRepository.saveAndFlush(radar);

    final Segment segment = new Segment();
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);

    final Segment segment1 = new Segment();
    segment1.setRadar(radar);
    segment1.setTitle("My new segment title");
    segment1.setDescription("My new segment description");
    segment1.setPosition(1);
    radar.setSegmentList(List.of(segment, segment1));
    List<Segment> segmentList = List.of(segment, segment1);
    for (Segment segmentAll : segmentList) {
      segmentRepository.save(segmentAll);
    }

    SegmentFilter segmentFilter = new SegmentFilter();
    segmentFilter.setTitle(segmentList.iterator().next().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<SegmentDto> segmentDtoPage = segmentService.findAll(segmentFilter, pageable);
    Assertions.assertEquals(10, segmentDtoPage.getSize());
    Assertions.assertEquals(0, segmentDtoPage.getNumber());
    Assertions.assertEquals(1, segmentDtoPage.getTotalPages());
    Assertions.assertEquals(1, segmentDtoPage.getNumberOfElements());
    Assertions.assertNotNull(segmentDtoPage.iterator().next().getId());
    Assertions.assertEquals(segmentDtoPage.iterator().next().getTitle(), segmentList.iterator().next().getTitle());
    Assertions.assertEquals(segmentDtoPage.iterator().next().getDescription(),
        segmentList.iterator().next().getDescription());
  }
}
