package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RingNumberValidator
    implements ConstraintValidator<RingNumberConstraint, Radar> {

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
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
