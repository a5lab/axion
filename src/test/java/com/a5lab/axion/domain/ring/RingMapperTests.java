package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.a5lab.axion.domain.AbstractMapperTests;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RingMapperTests  extends AbstractMapperTests {

  @MockBean
  private RadarRepository radarRepository;

  @Autowired
  private RingMapper ringMapper;

  @Test
  void testToDtoWithNull() {
    final var ringDto = ringMapper.toDto(null);

    Assertions.assertNull(ringDto);
  }

  @Test
  void testToDtoAllFields() {
    final Ring ring = new Ring();
    ring.setId(0L);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("color");
    ring.setPosition(0);
    ring.setActive(true);

    final var ringDto = ringMapper.toDto(ring);

    Assertions.assertEquals(ringDto.getTitle(), ring.getTitle());
    Assertions.assertEquals(ringDto.getDescription(), ring.getDescription());
    Assertions.assertEquals(ringDto.getColor(), ring.getColor());
    Assertions.assertEquals(ringDto.getPosition(), ring.getPosition());
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

    ring.setTechnologyBlipList(List.of(technologyBlip));

    RingDto ringDto = ringMapper.toDto(ring);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ring.getColor(), ringDto.getColor());
    Assertions.assertEquals(ring.getPosition(), ringDto.getPosition());

    Assertions.assertEquals(ring.getRadar().getId(), ringDto.getRadarId());
    Assertions.assertEquals(ring.getRadar().getTitle(), ringDto.getRadarTitle());

    Assertions.assertNotNull(ringDto.getTechnologyBlipList());
    Assertions.assertEquals(1, ringDto.getTechnologyBlipList().size());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getRadarTitle(), technologyBlip.getRadar().getTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getTechnologyId(), technologyBlip.getTechnology().getId());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getTechnologyTitle(), technologyBlip.getTechnology().getTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getTechnologyWebsite(), technologyBlip.getTechnology().getWebsite());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getTechnologyMoved(), technologyBlip.getTechnology().getMoved());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().isTechnologyActive(), technologyBlip.getTechnology().isActive());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getSegmentId(), technologyBlip.getSegment().getId());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getSegmentTitle(), technologyBlip.getSegment().getTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getSegmentPosition(), technologyBlip.getSegment().getPosition());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getRingId(), technologyBlip.getRing().getId());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getRingTitle(), technologyBlip.getRing().getTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getRingPosition(), technologyBlip.getRing().getPosition());
  }

  @Test
  void testToEntityAllFields() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setRadarId(radar.getId());
    ringDto.setTitle("My ring title1");
    ringDto.setDescription("My ring description1");
    ringDto.setColor("color");
    ringDto.setPosition(0);
    final var ring = ringMapper.toEntity(ringDto);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ringDto.getColor(), ring.getColor());
    Assertions.assertEquals(ringDto.getPosition(), ring.getPosition());

    Mockito.verify(radarRepository).findById(radar.getId());
  }

    /* TODO: add extra mockito

  @Test
  public void testToEntityAllLists() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setRadarId(radar.getId());
    ringDto.setRadarTitle(radar.getTitle());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description1");
    ringDto.setColor("color");
    ringDto.setPosition(0);

    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setRingId(ringDto.getId());
    ringDto.setTechnologyBlipList(List.of(technologyBlipDto));

    Ring ring = ringMapper.toEntity(ringDto);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ring.getColor(), ringDto.getColor());
    Assertions.assertEquals(ring.getPosition(), ringDto.getPosition());

    Assertions.assertEquals(ring.getRadar().getId(), ringDto.getRadarId());
    Assertions.assertEquals(ring.getRadar().getTitle(), ringDto.getRadarTitle());

    Assertions.assertNotNull(ringDto.getTechnologyBlipList());
    Assertions.assertEquals(1, ringDto.getTechnologyBlipList().size());
    Assertions.assertEquals(ringDto.getTechnologyBlipList().iterator().next().getId(), technologyBlipDto.getId());

    Mockito.verify(radarRepository).findById(radar.getId());
  }
  */
}