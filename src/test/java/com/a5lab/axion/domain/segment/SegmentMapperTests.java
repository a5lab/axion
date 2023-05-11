package com.a5lab.axion.domain.segment;


import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;

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
  public void testToDtoTechnologyBlipList() {
    final Radar radar = new Radar();
    radar.setId(0L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    final var segment = new Segment();
    segment.setId(1L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);

    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setSegment(segment);
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

  @Test
  public void testToEntityTechnologyBlipList() {
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
}
