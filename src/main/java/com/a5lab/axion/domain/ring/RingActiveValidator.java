package com.a5lab.axion.domain.ring;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RingActiveValidator
    implements ConstraintValidator<RingActiveConstraint, Ring> {

  @Override
  public boolean isValid(Ring ring, ConstraintValidatorContext context) {
    /*
    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
    */
    return true;
  }
}
