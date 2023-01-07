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
    final Tenant tenant = new Tenant();
    tenant.setTitle("my title");
    tenant.setDescription("my description");

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
    final TenantDto tenantDto = new TenantDto("my title1", "my description1");

    final var tenant = mapper.toEntity(tenantDto);

    Assertions.assertNull(tenant.getId());
    Assertions.assertEquals(tenant.getTitle(), tenantDto.getTitle());
    Assertions.assertEquals(tenant.getDescription(), tenantDto.getDescription());
  }
}