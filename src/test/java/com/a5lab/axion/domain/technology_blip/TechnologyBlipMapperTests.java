package com.a5lab.axion.domain.technology_blip;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TechnologyBlipMapperTests {

  private final TechnologyBlipMapper mapper = Mappers.getMapper(TechnologyBlipMapper.class);

  @Test
  void testToDtoWithNull() {
    final var technologyBlipDto = mapper.toDto(null);

    Assertions.assertNull(technologyBlipDto);
  }

  @Test
  void testToDtoAllFields() {
    // final TechnologyBlip technology_blip = new TechnologyBlip(0L, "title", "desciption");
    // final var technologyBlipDto = mapper.toDto(technology_blip);

    // Assertions.assertEquals(technologyBlipDto.getTitle(), technology_blip.getTitle());
    // Assertions.assertEquals(technologyBlipDto.getDescription(), technology_blip.getDescription());
  }

  @Test
  void testToEntityWithNull() {
    final var technology_blip = mapper.toEntity(null);

    Assertions.assertNull(technology_blip);
  }

  @Test
  void testToEntityAllFields() {
    // final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto(0L, "my title1", "my description1");
    // final var technology_blip = mapper.toEntity(technologyBlipDto);

    // Assertions.assertEquals(technology_blip.getId(), technologyBlipDto.getId());
    // Assertions.assertEquals(technology_blip.getTitle(), technologyBlipDto.getTitle());
    // Assertions.assertEquals(technology_blip.getDescription(), technologyBlipDto.getDescription());
  }
}