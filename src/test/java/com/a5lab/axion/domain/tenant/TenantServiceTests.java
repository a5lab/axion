package com.a5lab.axion.domain.tenant;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import com.a5lab.axion.domain.AbstractServiceTests;

class TenantServiceTests extends AbstractServiceTests {
  private TenantRepository tenantRepository = Mockito.mock(TenantRepository.class);

  private TenantMapper tenantMapper = Mappers.getMapper(TenantMapper.class);

  private TenantService tenantService = new TenantServiceImpl(tenantRepository, tenantMapper);

  @Test
  void shouldFindAllTenants() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantList = List.of(tenant);
    Mockito.when(tenantRepository.findAll(any(Sort.class))).thenReturn(tenantList);

    Collection<TenantDto> tenantDtoCollection = tenantService.findAll();
    Assertions.assertEquals(1, tenantDtoCollection.size());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getId(), tenant.getId());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getTitle(), tenant.getTitle());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getDescription(),
        tenant.getDescription());

  }

}
