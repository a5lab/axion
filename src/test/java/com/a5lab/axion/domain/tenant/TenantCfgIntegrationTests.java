package com.a5lab.axion.domain.tenant;

import java.util.LinkedList;
import java.util.List;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.a5lab.axion.domain.AbstractIntegrationTests;

class TenantCfgIntegrationTests extends AbstractIntegrationTests {

  @Autowired
  private TenantService tenantService;

  @Autowired
  private TenantRepository tenantRepository;

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
    TenantDto tenantDto = new TenantDto();
    tenantDto.setId(2L);
    tenantDto.setTitle("My title");
    tenantDto.setDescription("My description");
    List<Tenant> tenantListBefore = new LinkedList<>(tenantRepository.findAll());

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
    multiValueMap.add("title", tenantDto.getTitle());
    multiValueMap.add("description", tenantDto.getDescription());
    HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(multiValueMap, httpHeaders);
    ResponseEntity<String> responseEntity = restTemplate.postForEntity(
        baseUrl + port + "/settings/tenants/create", httpEntity, String.class);

    Assertions.assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());
    List<Tenant> tenantListAfter = new LinkedList<>(tenantRepository.findAll());

    Assertions.assertEquals(tenantListBefore.size()+1, tenantListAfter.size());

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
    List<Tenant> tenantListBefore = new LinkedList<>(tenantRepository.findAll());


    String url = String.format("/settings/tenants/delete/%d", tenantDto.getId());
    ResponseEntity<String> responseEntity =
        restTemplate.exchange(baseUrl + port + url, HttpMethod.GET, null, String.class);

    List<Tenant> tenantListAfter = new LinkedList<>(tenantRepository.findAll());

    Assertions.assertEquals(tenantListBefore.size()-1, tenantListAfter.size());
    // TODO: why OK, rather than FOUND. What the difference between update & delete
    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
  }
}