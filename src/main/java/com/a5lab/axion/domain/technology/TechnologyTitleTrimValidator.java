package com.a5lab.axion.domain.technology;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class TechnologyTitleTrimValidator implements ConstraintValidator<TechnologyTrimTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.length() == value.trim().length();
  }
}
