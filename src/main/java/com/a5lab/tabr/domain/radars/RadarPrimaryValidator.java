package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarPrimaryValidator implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    // https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/validator-customconstraints.html#validator-customconstraints-validator
    return false;
  }
}
