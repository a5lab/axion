package com.a5lab.tabr.domain.rings;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.a5lab.tabr.AbstractRepositoryTests;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RingDtoTests {

  @Autowired
  private Validator validator;

  @Test
  void ensureValidatorIsLoaded() {
    // https://stackoverflow.com/questions/29069956/how-to-test-validation-annotations-of-a-class-using-junit
    // Should we user hibernate-validator? But what is about dto?
    Assertions.assertNotNull(validator);
  }

  @Test
  void shouldFailOnNullTitle() {
    final Ring ring = new Ring();
    Set<ConstraintViolation<Ring>> violations = validator.validate(ring);
    Assertions.assertFalse(violations.isEmpty());
  }

  @Test
  void shouldFailOnBlankTitle() {
    final Ring ring = new Ring();
    ring.setTitle("");

    Set<ConstraintViolation<Ring>> violations = validator.validate(ring);
    Assertions.assertFalse(violations.isEmpty());
  }

  @Test
  void shouldBeOnOnNotBlankTitle() {
    final Ring ring = new Ring();
    ring.setTitle("Title");
    ring.setDescription("Description");

    Set<ConstraintViolation<Ring>> violations = validator.validate(ring);
    Assertions.assertTrue(violations.isEmpty());
  }


}