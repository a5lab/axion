package com.a5lab.tabr.domain.tenants;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.a5lab.tabr.AbstractRepositoryTest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class TenantRepositoryTest extends AbstractRepositoryTest {

  @Autowired
  private TenantRepository ringRepository;

  @Test
  void shouldSaveTenantWithTitleAndDescr() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
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
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Tenant r = new Tenant();
    r.setTitle("");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Tenant r = new Tenant();
    r.setTitle(" ");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription("");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Tenant r = new Tenant();
    r.setTitle("TEST");
    r.setDescription(" ");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnLowerTitle() {
    final Tenant r = new Tenant();
    r.setTitle("test");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFindSavedTenantById() {
    final Tenant r = new Tenant();
    r.setTitle("MY");
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());
    var id = r.getId();

    Assertions.assertTrue(ringRepository.findById(id).isPresent());
  }

  @Test
  void shouldFindSavedTenantByTitle() {
    String title = "SUPER";
    final Tenant r = new Tenant();
    r.setTitle(title);
    r.setDescription("Very good description for Tenant");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());

    Assertions.assertTrue(ringRepository.findTenantByTitle(title).isPresent());
  }

}