package com.a5lab.axion.domain.tenant;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TenantTitleValidator implements ConstraintValidator<TenantTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return value == null || value.length() == value.trim().length();
  }
}
