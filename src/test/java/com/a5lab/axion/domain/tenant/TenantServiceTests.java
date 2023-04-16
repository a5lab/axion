package com.a5lab.axion.domain.tenant;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

import com.a5lab.axion.domain.AbstractServiceTests;

class TenantServiceTests extends AbstractServiceTests {
  private final TenantRepository tenantRepository = Mockito.mock(TenantRepository.class);

  private final TenantMapper tenantMapper = Mappers.getMapper(TenantMapper.class);

  private final TenantService tenantService = new TenantServiceImpl(tenantRepository, tenantMapper);

  @Test
  void shouldFindAllTenants() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantList = List.of(tenant);
    Mockito.when(tenantRepository.findAll(any(Sort.class))).thenReturn(tenantList);

    Collection<TenantDto> tenantDtoCollection = tenantService.findAll();
    Assertions.assertEquals(1, tenantDtoCollection.size());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getId(), tenant.getId());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getTitle(), tenant.getTitle());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getDescription(), tenant.getDescription());

  }

  @Test
  void shouldFindAllTenantsWithFilter() {
    /*
    final Tenant tenantDto = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantDtoList = List.of(tenantDto);
    Page<Tenant> page = new PageImpl<>(tenantDtoList);
    Mockito.when(tenantRepository.findAll(any(Sort.class), any())).thenReturn(page);

    TenantFilter tenantFilter = new TenantFilter();
    Pageable pageable = new Pageable();

    Collection<TenantDto> tenantDtoCollection = tenantService.findAll(tenantFilter, pageable);
    Assertions.assertEquals(1, tenantDtoCollection.size());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getId(), tenant.getId());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getTitle(), tenant.getTitle());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getDescription(), tenant.getDescription());
    */
  }

  @Test
  void shouldFindByIdTenants() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    Mockito.when(tenantRepository.findById(tenant.getId())).thenReturn(Optional.of(tenant));

    Optional<TenantDto> tenantDtoOptional = tenantService.findById(tenant.getId());
    Assertions.assertTrue(tenantDtoOptional.isPresent());
    Assertions.assertEquals(tenant.getId(), tenantDtoOptional.get().getId());
    Assertions.assertEquals(tenant.getTitle(), tenantDtoOptional.get().getTitle());
    Assertions.assertEquals(tenant.getDescription(), tenantDtoOptional.get().getDescription());

    Mockito.verify(tenantRepository).findById(tenant.getId());
  }


  @Test
  void shouldSaveTenant() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    Mockito.when(tenantRepository.save(any())).thenReturn(tenant);

    TenantDto tenantDto = tenantService.save(tenantMapper.toDto(tenant));
    Assertions.assertEquals(tenant.getId(), tenantDto.getId());
    Assertions.assertEquals(tenant.getTitle(), tenantDto.getTitle());
    Assertions.assertEquals(tenant.getDescription(), tenantDto.getDescription());

    Mockito.verify(tenantRepository).save(any());
  }

  @Test
  void shouldDeleteTenant() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    Mockito.doAnswer((i) -> null).when(tenantRepository).deleteById(tenant.getId());

    tenantService.deleteById(tenant.getId());
    Mockito.verify(tenantRepository).deleteById(tenant.getId());
  }
}


