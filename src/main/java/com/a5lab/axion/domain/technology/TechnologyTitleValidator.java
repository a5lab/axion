package com.a5lab.axion.domain.technology;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class TechnologyTitleValidator implements ConstraintValidator<TechnologyTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.length() == value.trim().length();
  }
}
