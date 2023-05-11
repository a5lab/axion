package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
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
  public void testToDtoTechnologyBlipList() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("color");
    ring.setPosition(0);
    ring.setActive(true);

    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setRing(ring);
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
  }

  @Test
  void testToEntityWithNull() {
    final var ring = ringMapper.toEntity(null);

    Assertions.assertNull(ring);
  }
  /* TODO: RadarId and RadarTitle return null
  @Test
  void testToEntityRadarIdAndTitleWithNull() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(radar.getId())).thenReturn(Optional.empty());

    final RingDto ringDto = new RingDto();
    ringDto.setId(1L);
    ringDto.setRadarId(radar.getId());
    ringDto.setRadarTitle(radar.getTitle());

    Ring ring = ringMapper.toEntity(ringDto);

    Assertions.assertNull(ring.getRadar().getId());
    Assertions.assertNull(ring.getRadar().getTitle());
  }
   */

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
  public void testToEntityTechnologyBlipList() {
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
}