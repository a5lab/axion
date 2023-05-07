package com.a5lab.axion.domain.technology_blip;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;

import com.a5lab.axion.domain.technology.TechnologyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class TechnologyBlipMapperTests extends AbstractMapperTests {

  @MockBean
  private RadarRepository radarRepository;
  @MockBean
  private RingRepository ringRepository;
  @MockBean
  private SegmentRepository segmentRepository;
  @MockBean
  private TechnologyRepository technologyRepository ;
  @Autowired
  private TechnologyBlipMapper technologyBlipMapper;

  @Test
  void testToWithNull() {
    final var technologyBlipDto = technologyBlipMapper.toDto(null);

    Assertions.assertNull(technologyBlipDto);
  }

  @Test
  void testToAllFields() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment Description");
    segment.setPosition(0);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("My ring color");
    ring.setPosition(0);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology title");
    technology.setWebsite("My technology website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    final TechnologyBlip technology_blip = new TechnologyBlip();
    technology_blip.setId(10L);
    technology_blip.setRadar(radar);
    technology_blip.setSegment(segment);
    technology_blip.setRing(ring);
    technology_blip.setTechnology(technology);

    final var technologyBlipDto = technologyBlipMapper.toDto(technology_blip);

    Assertions.assertEquals(technologyBlipDto.getRadarId(), technology_blip.getRadar().getId());
    Assertions.assertEquals(technologyBlipDto.getSegmentId(), technology_blip.getSegment().getId());
    Assertions.assertEquals(technologyBlipDto.getRingId(), technology_blip.getRing().getId());
    Assertions.assertEquals(technologyBlipDto.getTechnologyId(), technology_blip.getTechnology().getId());
  }

  @Test
  void testToEntityWithNull() {
    final var technology_blip = technologyBlipMapper.toEntity(null);

    Assertions.assertNull(technology_blip);
  }

  @Test
  void testToEntityAllFields() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment Description");
    segment.setPosition(0);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("My ring color");
    ring.setPosition(0);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology title");
    technology.setWebsite("My technology website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    final TechnologyBlipDto technology_blipDto = new TechnologyBlipDto();
    technology_blipDto.setId(10L);
    technology_blipDto.setRadarId(radar.getId());
    technology_blipDto.setSegmentId(segment.getId());
    technology_blipDto.setRingId(ring.getId());
    technology_blipDto.setTechnologyId(technology.getId());

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));
    Mockito.when(ringRepository.findById(any())).thenReturn(Optional.of(ring));
    Mockito.when(segmentRepository.findById(any())).thenReturn(Optional.of(segment));
    Mockito.when(technologyRepository.findById(any())).thenReturn(Optional.of(technology));

    final var technology_blip = technologyBlipMapper.toEntity(technology_blipDto);

    Assertions.assertEquals(technology_blip.getId(), technology_blipDto.getId());
    Assertions.assertEquals(technology_blip.getRadar().getId(), technology_blipDto.getRadarId());
    Assertions.assertEquals(technology_blip.getSegment().getId(), technology_blipDto.getSegmentId());
    Assertions.assertEquals(technology_blip.getRing().getId(), technology_blipDto.getRingId());
    Assertions.assertEquals(technology_blip.getTechnology().getId(), technology_blipDto.getTechnologyId());

    Mockito.verify(radarRepository).findById(radar.getId());
    Mockito.verify(ringRepository).findById(ring.getId());
    Mockito.verify(segmentRepository).findById(segment.getId());
    Mockito.verify(technologyRepository).findById(technology.getId());
  }
}