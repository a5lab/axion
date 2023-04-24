package com.a5lab.axion.domain.ring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.AbstractMapperTests;

class RingMapperTests  extends AbstractMapperTests {

  private final RingMapper mapper = Mappers.getMapper(RingMapper.class);

  @Test
  void testToDtoWithNull() {
    final var ringDto = mapper.toDto(null);

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

    final var ringDto = mapper.toDto(ring);

    Assertions.assertEquals(ringDto.getTitle(), ring.getTitle());
    Assertions.assertEquals(ringDto.getDescription(), ring.getDescription());
    Assertions.assertEquals(ringDto.getColor(), ring.getColor());
    Assertions.assertEquals(ringDto.getPosition(), ring.getPosition());
  }

  @Test
  void testToEntityWithNull() {
    final var ring = mapper.toEntity(null);

    Assertions.assertNull(ring);
  }

  @Test
  void testToEntityAllFields() {
    final RingDto ringDto = new RingDto();
    ringDto.setId(0L);
    ringDto.setTitle("title1");
    ringDto.setDescription("description1");
    ringDto.setColor("color1");
    ringDto.setPosition(0);
    final var ring = mapper.toEntity(ringDto);

    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());
    Assertions.assertEquals(ringDto.getColor(), ring.getColor());
    Assertions.assertEquals(ringDto.getPosition(), ring.getPosition());
  }
}