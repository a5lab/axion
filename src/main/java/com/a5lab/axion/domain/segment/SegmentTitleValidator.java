package com.a5lab.axion.domain.segment;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class SegmentTitleValidator implements ConstraintValidator<SegmentTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.length() == value.trim().length();
  }
}
