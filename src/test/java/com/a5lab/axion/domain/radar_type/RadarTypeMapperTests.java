package com.a5lab.axion.domain.radar_type;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractMapperTests;

public class RadarTypeMapperTests extends AbstractMapperTests {

  @Autowired
  private RadarTypeMapper radarTypeMapper;

  @Test
  public void testToDtoWithNull() {
    final var radarTypeDto = radarTypeMapper.toDto(null);

    Assertions.assertNull(radarTypeDto);
  }

  @Test
  public void testToDtoAllFields() {
    final var radarType = new RadarType();
    radarType.setId(1L);
    radarType.setTitle("My radarType title");
    radarType.setDescription("My radarType description");
    radarType.setCode("My radarType code");

    final var radarTypeDto = radarTypeMapper.toDto(radarType);

    Assertions.assertEquals(radarType.getId(), radarTypeDto.getId());
    Assertions.assertEquals(radarType.getTitle(), radarTypeDto.getTitle());
    Assertions.assertEquals(radarType.getDescription(), radarTypeDto.getDescription());
    Assertions.assertEquals(radarType.getCode(), radarTypeDto.getCode());
  }

  @Test
  public void testToEntityWithNull() {
    final var radarType = radarTypeMapper.toEntity(null);

    Assertions.assertNull(radarType);
  }

  @Test
  public void testToEntityAllFields() {
    final var radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setTitle("My radarType title");
    radarTypeDto.setDescription("My radarType description");
    radarTypeDto.setCode("My radarType code");

    final var radarType = radarTypeMapper.toEntity(radarTypeDto);

    Assertions.assertEquals(radarType.getId(), radarTypeDto.getId());
    Assertions.assertEquals(radarType.getTitle(), radarTypeDto.getTitle());
    Assertions.assertEquals(radarType.getDescription(), radarTypeDto.getDescription());
    Assertions.assertEquals(radarType.getCode(), radarTypeDto.getCode());
  }
}
