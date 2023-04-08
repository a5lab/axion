package com.a5lab.axion.domain.tenant;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

import org.hibernate.validator.internal.engine.path.PathImpl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class TenantRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private TenantRepository tenantRepository;

  @Test
  void shouldSaveTenantWithAllFields() {
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

  @Test
  void shouldFailOnNullTitle() {
    final Tenant tenant = new Tenant();
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), "title");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("must not be blank"));
    }
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 2);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), "title");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("размер должен находиться в диапазоне от 1 до 64") ||
          constraintViolation.getMessage().equals("must not be blank") ||
          constraintViolation.getMessage().equals("size must be between 1 and 64"));
    }
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Tenant tenant = new Tenant();
    tenant.setTitle(" ");
    tenant.setDescription("Very good description for Tenant");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), "title");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("must not be blank"));
    }
  }

  @Test
  void shouldFailOnNullDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
          "description");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("must not be blank"));
    }
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");
    tenant.setDescription("");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 2);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), "description");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("размер должен находиться в диапазоне от 1 до 512") ||
          constraintViolation.getMessage().equals("must not be blank") ||
          constraintViolation.getMessage().equals("size must be between 1 and 512"));
    }
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Tenant tenant = new Tenant();
    tenant.setTitle("TEST");
    tenant.setDescription(" ");

    Assertions.assertNull(tenant.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> tenantRepository.saveAndFlush(tenant),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
          "description");
      Assertions.assertTrue(constraintViolation.getMessage().equals("не должно быть пустым") ||
          constraintViolation.getMessage().equals("must not be blank"));
    }
  }
}
