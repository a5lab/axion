package com.a5lab.tabr.domain.tenants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.util.ReflectionUtils;

class TenantMapperTests {

  private final TenantMapper mapper = Mappers.getMapper(TenantMapper.class);

  @Test
  void testToRecordWithNull() {
    final var result = mapper.toRecord(null);

    Assertions.assertNull(result);
  }

  @Test
  void testToRecordIdIsNull() {
    final Tenant t = new Tenant();
    t.setTitle("my title1");
    t.setDescription("my description1");

    final var result = mapper.toRecord(t);

    Assertions.assertNull(result.id());
    Assertions.assertEquals(result.title(), t.getTitle());
    Assertions.assertEquals(result.description(), t.getDescription());
  }

  @Test
  void testToRecordAllFields() throws NoSuchFieldException {
    final Tenant t = new Tenant();
    var field = Tenant.class.getDeclaredField("id");
    ReflectionUtils.makeAccessible(field);
    ReflectionUtils.setField(field, t, 123L);
    t.setTitle("my title");
    t.setDescription("my description");

    final var result = mapper.toRecord(t);

    Assertions.assertEquals(result.id(), t.getId());
    Assertions.assertEquals(result.title(), t.getTitle());
    Assertions.assertEquals(result.description(), t.getDescription());
  }

  @Test
  void testToEntityWithNull() {
    final var result = mapper.toEntity(null);

    Assertions.assertNull(result);
  }

  @Test
  void testToEntityIdIsNull() {
    final TenantDTO record = new TenantDTO(null, "my title1", "my description1");

    final var result = mapper.toEntity(record);

    Assertions.assertNull(result.getId());
    Assertions.assertEquals(result.getTitle(), record.title());
    Assertions.assertEquals(result.getDescription(), record.description());
  }

  @Test
  void testToEntityAllFields() {
    final TenantDTO record = new TenantDTO(1L, "my title1", "my description1");

    final var result = mapper.toEntity(record);

    Assertions.assertNull(result.getId());
    Assertions.assertEquals(result.getTitle(), record.title());
    Assertions.assertEquals(result.getDescription(), record.description());
  }
}