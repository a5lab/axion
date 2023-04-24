package com.a5lab.axion.domain.technology_blip;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology.TechnologyDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TechnologyBlipMapperTests extends AbstractMapperTests {

  // private final TechnologyBlipMapper mapper = Mappers.getMapper(TechnologyBlipMapper.class);

  @Autowired
  private TechnologyBlipMapper mapper;


  @Test
  void testToDtoWithNull() {
    final var technologyBlipDto = mapper.toDto(null);

    Assertions.assertNull(technologyBlipDto);
  }

  @Test
  void testToDtoAllFields() {
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

    final var technologyBlipDto = mapper.toDto(technology_blip);

    Assertions.assertEquals(technologyBlipDto.getRadar().getId(), technology_blip.getRadar().getId());
    Assertions.assertEquals(technologyBlipDto.getSegment().getId(), technology_blip.getSegment().getId());
    Assertions.assertEquals(technologyBlipDto.getRing().getId(), technology_blip.getRing().getId());
    Assertions.assertEquals(technologyBlipDto.getTechnology().getId(), technology_blip.getTechnology().getId());
  }

  @Test
  void testToEntityWithNull() {
    final var technology_blip = mapper.toEntity(null);

    Assertions.assertNull(technology_blip);
  }

  @Test
  void testToEntityAllFields() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(radar);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment Description");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radar);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setColor("My ring color");
    ringDto.setPosition(0);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology title");
    technologyDto.setWebsite("My technology website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    final TechnologyBlipDto technology_blipDto = new TechnologyBlipDto();
    technology_blipDto.setId(10L);
    technology_blipDto.setRadar(radar);
    technology_blipDto.setSegment(segmentDto);
    technology_blipDto.setRing(ringDto);
    technology_blipDto.setTechnology(technologyDto);

    final var technology_blip = mapper.toEntity(technology_blipDto);

    Assertions.assertEquals(technology_blip.getId(), technology_blipDto.getId());
    Assertions.assertEquals(technology_blip.getRadar().getId(), technology_blipDto.getRadar().getId());
    Assertions.assertEquals(technology_blip.getSegment().getId(), technology_blipDto.getSegment().getId());
    Assertions.assertEquals(technology_blip.getRing().getId(), technology_blipDto.getRing().getId());
    Assertions.assertEquals(technology_blip.getTechnology().getId(), technology_blipDto.getTechnology().getId());
  }
}