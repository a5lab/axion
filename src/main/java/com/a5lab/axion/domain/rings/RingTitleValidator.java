package com.a5lab.axion.domain.rings;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;


public class RingTitleValidator implements ConstraintValidator<RingTitleConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isAllUpperCase(value);
  }
}
