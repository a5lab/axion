package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.a5lab.axion.domain.AbstractMapperTests;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RingMapperTests  extends AbstractMapperTests {

  @MockBean
  private RadarRepository radarRepository;

  @MockBean
  private TechnologyBlipMapper technologyBlipMapper;

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
    radar.setRingList(List.of(new Ring()));
    radar.setTechnologyBlipList(List.of(new TechnologyBlip()));

    // Create ring
    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setRadar(radar);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(1);
    ring.setActive(true);
    ring.setTechnologyBlipList(List.of(new TechnologyBlip()));

    // Create segment
    final var segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(2);
    segmentDto.setActive(true);

    // Create technology
    final var technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology title");
    technologyDto.setWebsite("https://www.example.com");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    // Create technologyBlip
    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radar.getId());
    technologyBlipDto.setRingId(ring.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.when(technologyBlipMapper.toDto(any())).thenReturn(technologyBlipDto);

    RingDto ringDto = ringMapper.toDto(ring);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ring.getColor(), ringDto.getColor());
    Assertions.assertEquals(ring.getPosition(), ringDto.getPosition());

    Assertions.assertEquals(ring.getRadar().getId(), ringDto.getRadarId());
    Assertions.assertEquals(ring.getRadar().getTitle(), ringDto.getRadarTitle());

    Assertions.assertNotNull(ringDto.getTechnologyBlipDtoList());
    Assertions.assertEquals(1, ringDto.getTechnologyBlipDtoList().size());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getId(), technologyBlipDto.getId());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getRadarId(), technologyBlipDto.getRadarId());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getRadarTitle(), technologyBlipDto.getRadarTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getTechnologyId(), technologyBlipDto.getTechnologyId());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getTechnologyTitle(), technologyBlipDto.getTechnologyTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getTechnologyWebsite(), technologyBlipDto.getTechnologyWebsite());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getTechnologyMoved(), technologyBlipDto.getTechnologyMoved());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().isTechnologyActive(), technologyBlipDto.isTechnologyActive());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getSegmentId(), technologyBlipDto.getSegmentId());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getSegmentTitle(), technologyBlipDto.getSegmentTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getSegmentPosition(), technologyBlipDto.getSegmentPosition());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getRingId(), technologyBlipDto.getRingId());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getRingTitle(), technologyBlipDto.getRingTitle());
    Assertions.assertEquals(ringDto.getTechnologyBlipDtoList().iterator().next().getRingPosition(), technologyBlipDto.getRingPosition());

    Mockito.verify(technologyBlipMapper).toDto(any());
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

  @Test
  public void testToEntityAllLists() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("My color");
    ring.setPosition(1);
    ring.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setRadarId(radar.getId());
    ringDto.setRadarTitle(radar.getTitle());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description1");
    ringDto.setColor("color");
    ringDto.setPosition(0);
    ringDto.setTechnologyBlipDtoList(List.of(new TechnologyBlipDto()));

    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(3L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);

    Mockito.when(technologyBlipMapper.toEntity(any())).thenReturn(technologyBlip);

    Ring mapped_ring = ringMapper.toEntity(ringDto);

    Assertions.assertEquals(mapped_ring.getId(), ringDto.getId());
    Assertions.assertEquals(mapped_ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(mapped_ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(mapped_ring.getColor(), ringDto.getColor());
    Assertions.assertEquals(mapped_ring.getPosition(), ringDto.getPosition());

    Assertions.assertEquals(mapped_ring.getRadar().getId(), ringDto.getRadarId());
    Assertions.assertEquals(mapped_ring.getRadar().getTitle(), ringDto.getRadarTitle());

    Assertions.assertNotNull(mapped_ring.getTechnologyBlipList());
    Assertions.assertEquals(1, mapped_ring.getTechnologyBlipList().size());
    Assertions.assertEquals(mapped_ring.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(mapped_ring.getTechnologyBlipList().iterator().next().getRadar().getId(), technologyBlip.getRadar().getId());
    Assertions.assertEquals(mapped_ring.getTechnologyBlipList().iterator().next().getRing().getId(), technologyBlip.getRing().getId());

    Mockito.verify(radarRepository).findById(radar.getId());
    Mockito.verify(technologyBlipMapper).toEntity(any());
  }
}