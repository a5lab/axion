package com.a5lab.axion.domain.tenant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

  @Test
  public void shouldShowTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);

    String url = String.format("/settings/tenants/show/%d", tenantDto.getId());
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + url, HttpMethod.GET, null, String.class);
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

  /*
  @Test
  public void shouldCreateTenants() {
    // TODO: here is logic problem: we should avoid id, it can be changed(?)
    // We need cound records and check that count +1 and get last id

    TenantDto tenantDto = new TenantDto();
    tenantDto.setId(1L);
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("title", tenantDto.getTitle());
    multiValueMap.add("description", tenantDto.getDescription());
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        baseUrl + port + "/settings/tenants/create", httpEntity, String.class);

    Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    // TODO: get last id from database and compare with tenantDto

    this.tenantService.deleteById(tenantDto.getId());
  }
   */


  @Test
  public void shouldEditTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);

    String url = String.format("/settings/tenants/edit/%d", tenantDto.getId());
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + url, HttpMethod.GET, null, String.class);
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getTitle()));
    Assertions.assertTrue(responseEntity.getBody().contains(tenantDto.getDescription()));

    this.tenantService.deleteById(tenantDto.getId());
  }

  @Test
  public void shouldUpdateTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("id", tenantDto.getId().toString());
    multiValueMap.add("title", tenantDto.getTitle());
    multiValueMap.add("description", tenantDto.getDescription());
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        baseUrl + port + "/settings/tenants/create", httpEntity, String.class);

    Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    Optional<TenantDto> tenantDtoOptional = this.tenantService.findById(tenantDto.getId());
    Assertions.assertEquals(tenantDto.getId(), tenantDtoOptional.get().getId());
    Assertions.assertEquals(tenantDto.getTitle(), tenantDtoOptional.get().getTitle());
    Assertions.assertEquals(tenantDto.getDescription(), tenantDtoOptional.get().getDescription());

    this.tenantService.deleteById(tenantDto.getId());
  }

  @Test
  public void shouldDeleteTenants() {
    TenantDto tenantDto = new TenantDto();
    tenantDto.setId(1L);
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    tenantDto = tenantService.save(tenantDto);

    String url = String.format("/settings/tenants/delete/%d", tenantDto.getId());
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + url, HttpMethod.GET, null, String.class);
    // TODO: why OK, rather than FOUND. What the difference between update & delete
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}