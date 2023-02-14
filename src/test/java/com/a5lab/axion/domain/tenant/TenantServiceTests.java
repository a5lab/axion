package com.a5lab.axion.domain.tenant;

import java.util.Collection;
import java.util.List;

import com.a5lab.axion.domain.AbstractServiceTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class TenantServiceTests extends AbstractServiceTests {

  @Autowired
  private TenantService tenantService;

  @MockBean
  private TenantRepository tenantRepository;

  @Test
  void shouldFindAllTenants() {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantList = List.of(tenant);
    Mockito.when(tenantRepository.findAll()).thenReturn(tenantList);

    Collection<TenantDto> tenantDtoCollection = tenantService.findAll();
    Assertions.assertEquals(tenantDtoCollection.size(), 1);
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getId(), tenant.getId());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getTitle(), tenant.getTitle());
    Assertions.assertEquals(tenantDtoCollection.iterator().next().getDescription(),
        tenant.getDescription());

  }

}
