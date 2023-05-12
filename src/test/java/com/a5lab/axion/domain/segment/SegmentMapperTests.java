package com.a5lab.axion.domain.segment;


import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;

import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class SegmentMapperTests  extends AbstractMapperTests {

  @MockBean
  private RadarRepository radarRepository;

  @Autowired
  private SegmentMapper segmentMapper;

  @Test
  public void testToDtoWithNull() {
    final var segmentDto = segmentMapper.toDto(null);

    Assertions.assertNull(segmentDto);
  }

  @Test
  public void testToDtoAllFields() {
    final var segment = new Segment();
    segment.setId(1L);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);

    final var segmentDto = segmentMapper.toDto(segment);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());
  }

  @Test
  public void testToDtoAllLists() {
    // Create radar
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My title");
    radar.setDescription("My description");
    radar.setPrimary(true);
    radar.setActive(true);

    // Create ring
    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setRadar(radar);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(1);
    ring.setActive(true);
    radar.setRingList(List.of(ring));

    // Create segment
    final Segment segment = new Segment();
    segment.setId(3L);
    segment.setRadar(radar);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(2);
    segment.setActive(true);
    radar.setSegmentList(List.of(segment));

    // Create technology
    final var technology = new Technology();
    technology.setId(4L);
    technology.setTitle("My  title");
    technology.setWebsite("https://www.example.com");
    technology.setDescription("My description");
    technology.setMoved(1);
    technology.setActive(true);

    // Create technologyBlip
    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(5L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);
    radar.setTechnologyBlipList(List.of(technologyBlip));

    segment.setTechnologyBlipList(List.of(technologyBlip));

    final var segmentDto = segmentMapper.toDto(segment);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Assertions.assertEquals(segment.getRadar().getId(), segmentDto.getRadarId());
    Assertions.assertEquals(segment.getRadar().getTitle(), segmentDto.getRadarTitle());

    Assertions.assertNotNull(segmentDto.getTechnologyBlipList());
    Assertions.assertEquals(1, segmentDto.getTechnologyBlipList().size());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getRadarTitle(), technologyBlip.getRadar().getTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getTechnologyId(), technologyBlip.getTechnology().getId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getTechnologyTitle(), technologyBlip.getTechnology().getTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getTechnologyWebsite(), technologyBlip.getTechnology().getWebsite());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getTechnologyMoved(), technologyBlip.getTechnology().getMoved());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().isTechnologyActive(), technologyBlip.getTechnology().isActive());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getSegmentId(), technologyBlip.getSegment().getId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getSegmentTitle(), technologyBlip.getSegment().getTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getSegmentPosition(), technologyBlip.getSegment().getPosition());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getRingId(), technologyBlip.getRing().getId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getRingTitle(), technologyBlip.getRing().getTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getRingPosition(), technologyBlip.getRing().getPosition());
  }

  @Test
  public void testToEntityWithNull() {
    final var segment = segmentMapper.toEntity(null);

    Assertions.assertNull(segment);
  }

  @Test
  public void testToEntityAllFields() {
    final Radar radar = new Radar();
    radar.setId(0L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final var segmentDto = new SegmentDto();
    segmentDto.setId(1L);
    segmentDto.setRadarId(radar.getId());
    segmentDto.setRadarTitle(radar.getTitle());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);

    final var segment = segmentMapper.toEntity(segmentDto);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Mockito.verify(radarRepository).findById(segment.getRadar().getId());
  }

  /* TODO: add extra mockito
  @Test
  public void testToEntityAllLists() {
    final Radar radar = new Radar();
    radar.setId(0L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final var segmentDto = new SegmentDto();
    segmentDto.setId(1L);
    segmentDto.setRadarId(radar.getId());
    segmentDto.setRadarTitle(radar.getTitle());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);

    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setSegmentId(segmentDto.getId());
    segmentDto.setTechnologyBlipList(List.of(technologyBlipDto));

    final var segment = segmentMapper.toEntity(segmentDto);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Assertions.assertEquals(segment.getRadar().getId(), segmentDto.getRadarId());
    Assertions.assertEquals(segment.getRadar().getTitle(), segmentDto.getRadarTitle());

    Assertions.assertNotNull(segmentDto.getTechnologyBlipList());
    Assertions.assertEquals(1, segmentDto.getTechnologyBlipList().size());
    Assertions.assertEquals(segmentDto.getTechnologyBlipList().iterator().next().getId(), technologyBlipDto.getId());

    Mockito.verify(radarRepository).findById(segment.getRadar().getId());
  }
  */
}
