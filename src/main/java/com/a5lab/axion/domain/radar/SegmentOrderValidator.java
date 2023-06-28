package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentOrderValidator
    implements ConstraintValidator<SegmentOrderConstraint, Radar> {

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    return true;
  }
}
