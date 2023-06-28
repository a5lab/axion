package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentNumberValidator
    implements ConstraintValidator<SegmentNumberConstraint, Radar> {

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    return true;
  }
}
