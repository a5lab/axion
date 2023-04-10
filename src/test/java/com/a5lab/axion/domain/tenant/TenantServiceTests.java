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
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getDescription(), tenant.getDescription());

  }

  @Test
  void shouldFindAllTenantsWithFilter() {
    // TODO:  See TenantsSerivces:
    // test Page<TenantDto> findAll(TenantFilter tenantFilter, Pageable pageable);
    /*
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    //TenantFilter tenantFilter = new TenantFilter();
    List<Tenant> tenantList = List.of(tenant);
    Mockito.when(tenantRepository.findAll(any(tenantFilter))).
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
    Mockito.when(tenantRepository.save(tenant)).thenReturn(tenant);

    TenantDto tenantDto = tenantService.save(tenantMapper.toDto(tenant));
    /* Why tenantDto is null?
    Assertions.assertEquals(tenant.getId(), tenantDto.getId());
    Assertions.assertEquals(tenant.getTitle(), tenantDto.getTitle());
    Assertions.assertEquals(tenant.getDescription(), tenantDto.getDescription());
    */

    Mockito.verify(tenantRepository).save(tenantMapper.toEntity(tenantDto));
  }

  @Test
  void shouldDeleteTenant() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    // How to mock method that return void?
    // Mockito.when(tenantRepository.deleteById(tenant.getId())).thenReturn();

    tenantService.deleteById(tenant.getId());
    // Why this test is working if we are not mock deleteById?
    Mockito.verify(tenantRepository).deleteById(tenant.getId());
  }
}


