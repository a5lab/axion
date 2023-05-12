package com.a5lab.axion.domain.tenant;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.a5lab.axion.domain.AbstractApiIntegrationTests;


class TenantApiIntegrationTests extends AbstractApiIntegrationTests {

  @Autowired
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() {
    // Create tenant
    final TenantDto tenantDto = new TenantDto();
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantService.save(tenantDto);

    ResponseEntity<List<Tenant>> responseEntity =
        this.restTemplate.exchange(baseUrl + port + "/api/v1/tenants", HttpMethod.GET, null,
            new ParameterizedTypeReference<List<Tenant>>() {
            });
    List<Tenant> tenantList = responseEntity.getBody();
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertEquals(1, tenantList.size());

    this.tenantService.deleteById(tenantList.iterator().next().getId());
  }
}