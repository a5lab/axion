package com.a5lab.tabr.domain.tenants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;


public class TenantTitleValidator implements ConstraintValidator<TenantTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isAllUpperCase(value);
  }

}
