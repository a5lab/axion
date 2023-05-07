package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
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
    ring.setTitle("title");
    ring.setDescription("description");
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
  void testToEntityWithNull() {
    final var ring = ringMapper.toEntity(null);

    Assertions.assertNull(ring);
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
    ringDto.setTitle("title1");
    ringDto.setDescription("description1");
    ringDto.setColor("color1");
    ringDto.setPosition(0);
    final var ring = ringMapper.toEntity(ringDto);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ringDto.getColor(), ring.getColor());
    Assertions.assertEquals(ringDto.getPosition(), ring.getPosition());

    Mockito.verify(radarRepository).findById(radar.getId());
  }
}