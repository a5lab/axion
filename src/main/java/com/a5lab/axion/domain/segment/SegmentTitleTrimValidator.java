package com.a5lab.axion.domain.segment;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class SegmentTitleTrimValidator implements ConstraintValidator<SegmentTitleTrimConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.length() == value.trim().length();
  }
}
