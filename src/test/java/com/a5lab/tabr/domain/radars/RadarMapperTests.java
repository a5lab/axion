package com.a5lab.tabr.domain.radars;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class RadarMapperTests {

  private final RadarMapper mapper = Mappers.getMapper(RadarMapper.class);

  @Test
  void testToDtoWithNull() {
    final var radarDto = mapper.toDto(null);

    Assertions.assertNull(radarDto);
  }

  @Test
  void testToDtoAllFields() {
    final Radar radar = new Radar(0L, "title", "desciption", false);
    final var radarDto = mapper.toDto(radar);

    Assertions.assertEquals(radarDto.getId(), radar.getId());
    Assertions.assertEquals(radarDto.getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDto.getDescription(), radar.getDescription());
    Assertions.assertEquals(radarDto.isPrimary(), radar.isPrimary());
  }

  @Test
  void testToEntityWithNull() {
    final var radar = mapper.toEntity(null);

    Assertions.assertNull(radar);
  }

  @Test
  void testToEntityAllFields() {
    final RadarDto radarDto = new RadarDto(0L, "my title1", "my description1", false);
    final var radar = mapper.toEntity(radarDto);

    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());
    Assertions.assertEquals(radarDto.isPrimary(), radar.isPrimary());
  }
}