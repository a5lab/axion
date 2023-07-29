package com.a5lab.axion.domain.tenant;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.AbstractServiceTests;

class TenantServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private TenantRepository tenantRepository;
  @Autowired
  private TenantService tenantService;

  @Test
  @Transactional
  void shouldFindAllTenantsWithNullFilter() {
    List<Tenant> tenantList = List.of(
        new Tenant(null, "My title", "My description"),
        new Tenant(null, "His title", "His description"));
    for (Tenant tenant : tenantList) {
      tenantRepository.save(tenant);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TenantDto> tenantDtoPage = tenantService.findAll(null, pageable);
    Assertions.assertEquals(10, tenantDtoPage.getSize());
    Assertions.assertEquals(0, tenantDtoPage.getNumber());
    Assertions.assertEquals(1, tenantDtoPage.getTotalPages());
    Assertions.assertEquals(2, tenantDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllTenantsWithBlankTitleFilter() {
    List<Tenant> tenantList = List.of(
        new Tenant(null, "My title", "My description"),
        new Tenant(null, "His title", "His description"));
    for (Tenant tenant : tenantList) {
      tenantRepository.save(tenant);
    }

    TenantFilter tenantFilter = new TenantFilter();
    tenantFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TenantDto> tenantDtoPage = tenantService.findAll(tenantFilter, pageable);
    Assertions.assertEquals(10, tenantDtoPage.getSize());
    Assertions.assertEquals(0, tenantDtoPage.getNumber());
    Assertions.assertEquals(1, tenantDtoPage.getTotalPages());
    Assertions.assertEquals(2, tenantDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllTenantsWithTitleFilter() {
    List<Tenant> tenantList = List.of(
        new Tenant(null, "My title", "My description"),
        new Tenant(null, "His title", "His description"));
    for (Tenant tenant : tenantList) {
      tenantRepository.save(tenant);
    }

    TenantFilter tenantFilter = new TenantFilter();
    tenantFilter.setTitle(tenantList.iterator().next().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TenantDto> tenantDtoPage = tenantService.findAll(tenantFilter, pageable);
    Assertions.assertEquals(10, tenantDtoPage.getSize());
    Assertions.assertEquals(0, tenantDtoPage.getNumber());
    Assertions.assertEquals(1, tenantDtoPage.getTotalPages());
    Assertions.assertEquals(1, tenantDtoPage.getNumberOfElements());
    Assertions.assertNotNull(tenantDtoPage.iterator().next().getId());
    Assertions.assertEquals(tenantDtoPage.iterator().next().getTitle(), tenantList.iterator().next().getTitle());
    Assertions.assertEquals(tenantDtoPage.iterator().next().getDescription(),
        tenantList.iterator().next().getDescription());
  }
}
