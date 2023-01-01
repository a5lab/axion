package com.a5lab.tabr.domain.tenants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TenantTitleValidator implements ConstraintValidator<TenantTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return true;
  }

}
