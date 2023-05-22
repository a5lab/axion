package com.a5lab.axion.domain.tenant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.result.FlashAttributeResultMatchers;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.axion.domain.AbstractIntegrationTests;
import com.a5lab.axion.utils.FlashMessages;

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

  @Test
  public void shouldShowTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setId(1L);
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);
    this.tenantService.findById(tenantDto.getId());

    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + "/settings/tenants/show/1", HttpMethod.GET, null, String.class);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getTitle()));
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getDescription()));

    this.tenantService.deleteById(tenantDto.getId());
  }

  @Test
  public void shouldAddTenants() {
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + "/settings/tenants/add", HttpMethod.GET, null, String.class);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }

  @Test
  public void shouldCreateTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setId(1L);
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