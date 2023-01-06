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
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    tenantRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());
    Assertions.assertNotNull(r.getCreatedBy());
    Assertions.assertNotNull(r.getCreatedDate());
    Assertions.assertNotNull(r.getLastModifiedBy());
    Assertions.assertNotNull(r.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Tenant r = new Tenant();
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Tenant r = new Tenant();
    r.setTitle("");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Tenant r = new Tenant();
    r.setTitle(" ");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription("");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription(" ");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> tenantRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }


  @Test
  void shouldFindSavedTenantById() {
    final Tenant r = new Tenant();
    r.setTitle("MY");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    tenantRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());
    var id = r.getId();

    Assertions.assertTrue(tenantRepository.findById(id).isPresent());
  }
}