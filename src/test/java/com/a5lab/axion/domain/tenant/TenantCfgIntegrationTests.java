package com.a5lab.axion.domain.tenant;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.a5lab.axion.domain.AbstractIntegrationTests;


class TenantCfgIntegrationTests extends AbstractIntegrationTests {

  @Autowired
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() {
    // Create tenant
    TenantDto tenantDto = new TenantDto();
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + "/settings/tenants", HttpMethod.GET, null, String.class);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getTitle()));
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getDescription()));

    this.tenantService.deleteById(tenantDto.getId());
  }
}