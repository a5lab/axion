package com.a5lab.axion.domain.ring;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RingActiveValidator
    implements ConstraintValidator<RingActiveConstraint, Ring> {

  @Override
  public boolean isValid(Ring ring, ConstraintValidatorContext context) {
    if (ring.getRadar().isActive()) {
      return false;
    }
    return true;
  }
}
