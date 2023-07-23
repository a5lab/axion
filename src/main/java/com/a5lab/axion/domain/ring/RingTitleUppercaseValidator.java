package com.a5lab.axion.domain.ring;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;


public class RingTitleUppercaseValidator implements ConstraintValidator<RingTitleUppercaseConstraint, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return StringUtils.isAllUpperCase(value);
  }
}
