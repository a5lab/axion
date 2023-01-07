package com.a5lab.tabr.domain.tenants;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.a5lab.tabr.AbstractRepositoryTests;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TenantRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private TenantRepository tenantRepository;

  @Test
  void shouldSaveTenantWithTitleAndDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    tenantRepository.saveAndFlush(tenant);
    Assertions.assertNotNull(tenant.getId());
    Assertions.assertNotNull(tenant.getCreatedBy());
    Assertions.assertNotNull(tenant.getCreatedDate());
    Assertions.assertNotNull(tenant.getLastModifiedBy());
    Assertions.assertNotNull(tenant.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Tenant tenant = new Tenant();
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Tenant tenant = new Tenant();
    tenant.setTitle(" ");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");
    tenant.setDescription("");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");
    tenant.setDescription(" ");

    Assertions.assertNull(tenant.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(tenant))
        .isInstanceOf(ValidationException.class);
  }


  @Test
  void shouldFindSavedTenantById() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("MY");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    tenantRepository.saveAndFlush(tenant);
    Assertions.assertNotNull(tenant.getId());
    var id = tenant.getId();

    Assertions.assertTrue(tenantRepository.findById(id).isPresent());
  }
}