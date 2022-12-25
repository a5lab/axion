package com.a5lab.tabr.domain.tenants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.util.ReflectionUtils;

class TenantMapperTest {

  private final TenantMapper mapper = Mappers.getMapper(TenantMapper.class);

  @Test
  void testToDtoWithNull() {
    final var result = mapper.toDto(null);

    Assertions.assertNull(result);
  }

  @Test
  void testAllFields() throws NoSuchFieldException {
    final Tenant t = new Tenant();
    var field = Tenant.class.getDeclaredField("id");
    ReflectionUtils.makeAccessible(field);
    ReflectionUtils.setField(field, t, 123L);
    t.setTitle("my title");
    t.setDescription("my description");

    final var result = mapper.toDto(t);

    Assertions.assertEquals(result.id(), t.getId());
    Assertions.assertEquals(result.title(), t.getTitle());
    Assertions.assertEquals(result.description(), t.getDescription());
  }

  @Test
  void testIdIsNull() throws NoSuchFieldException {
    final Tenant t = new Tenant();
    t.setTitle("my title1");
    t.setDescription("my description1");

    final var result = mapper.toDto(t);

    Assertions.assertNull(result.id());
    Assertions.assertEquals(result.title(), t.getTitle());
    Assertions.assertEquals(result.description(), t.getDescription());
  }

}