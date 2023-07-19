package com.a5lab.axion.domain.technology;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class TechnologyRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private TechnologyRepository technologyRepository;

  @Test
  void shouldSaveTechnologyWithAllFields() {
    final Technology technology = new Technology();
    technology.setTitle("TEST");
    technology.setDescription("Very good description for Technology");

    Assertions.assertNull(technology.getId());
    technologyRepository.saveAndFlush(technology);
    Assertions.assertNotNull(technology.getId());
    Assertions.assertNotNull(technology.getCreatedBy());
    Assertions.assertNotNull(technology.getCreatedDate());
    Assertions.assertNotNull(technology.getLastModifiedBy());
    Assertions.assertNotNull(technology.getLastModifiedDate());
  }

  @Test
  void shouldFindSavedTechnologyById() {
    final Technology technology = new Technology();
    technology.setTitle("MY");
    technology.setDescription("Very good description for Technology");

    Assertions.assertNull(technology.getId());
    technologyRepository.saveAndFlush(technology);
    Assertions.assertNotNull(technology.getId());
    var id = technology.getId();

    Assertions.assertTrue(technologyRepository.findById(id).isPresent());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Technology technology = new Technology();
    technology.setDescription("Very good description for Technology");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "title");
      Assertions.assertEquals(constraintViolation.getMessage(), "must not be blank");
    }
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Technology technology = new Technology();
    technology.setTitle("");
    technology.setDescription("Very good description for Technology");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 2);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "title");
      Assertions.assertTrue(constraintViolation.getMessage().equals("must not be blank")
          || constraintViolation.getMessage().equals("size must be between 1 and 64"));
    }
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Technology technology = new Technology();
    technology.setTitle(" ");
    technology.setDescription("Very good description for Technology");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "title");
      Assertions.assertEquals(constraintViolation.getMessage(), "must not be blank");
    }
  }

  @Test
  void shouldFailOnNullDescription() {
    final Technology technology = new Technology();
    technology.setTitle("TEST");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "description");
      Assertions.assertEquals(constraintViolation.getMessage(), "must not be blank");
    }
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Technology technology = new Technology();
    technology.setTitle("TEST");
    technology.setDescription("");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 2);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "description");
      Assertions.assertTrue(constraintViolation.getMessage().equals("must not be blank")
          || constraintViolation.getMessage().equals("size must be between 1 and 512"));
    }
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Technology technology = new Technology();
    technology.setTitle("TEST");
    technology.setDescription(" ");

    Assertions.assertNull(technology.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> technologyRepository.saveAndFlush(technology),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(constraintViolation.getPropertyPath().toString(), "description");
      Assertions.assertEquals(constraintViolation.getMessage(), "must not be blank");
    }
  }

  @Test
  void shouldFailToSaveTechnologyDueToTitleWithRightWhiteSpace() {
    final Technology technology = new Technology();
    technology.setTitle("My new test Technology ");

    Assertions.assertNull(technology.getId());
    assertThatThrownBy(() -> technologyRepository.saveAndFlush(technology))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailToSaveTechnologyDueToTitleWithLeftWhiteSpace() {
    final Technology technology = new Technology();
    technology.setTitle(" My new test Technology");

    Assertions.assertNull(technology.getId());
    assertThatThrownBy(() -> technologyRepository.saveAndFlush(technology))
        .isInstanceOf(ValidationException.class);
  }
}
