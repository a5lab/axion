package com.a5lab.tabr.domain.tenants;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class TenantMapperTests {

  private final TenantMapper mapper = Mappers.getMapper(TenantMapper.class);

  @Test
  void testToDtoWithNull() {
    final var tenantDto = mapper.toDto(null);

    Assertions.assertNull(tenantDto);
  }

  @Test
  void testToDtoAllFields() {
    final Tenant tenant = new Tenant(0L, "title", "desciption");
    final var tenantDto = mapper.toDto(tenant);

    Assertions.assertEquals(tenantDto.getTitle(), tenant.getTitle());
    Assertions.assertEquals(tenantDto.getDescription(), tenant.getDescription());
  }

  @Test
  void testToEntityWithNull() {
    final var tenant = mapper.toEntity(null);

    Assertions.assertNull(tenant);
  }

  @Test
  void testToEntityAllFields() {
    final TenantDto tenantDto = new TenantDto(0L, "my title1", "my description1");
    final var tenant = mapper.toEntity(tenantDto);

    Assertions.assertEquals(tenant.getId(), tenantDto.getId());
    Assertions.assertEquals(tenant.getTitle(), tenantDto.getTitle());
    Assertions.assertEquals(tenant.getDescription(), tenantDto.getDescription());
  }
}