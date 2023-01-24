package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarDtoPrimaryValidator
    implements ConstraintValidator<RadarDtoPrimaryConstraint, RadarDto> {

  @Override
  public boolean isValid(RadarDto radarDto, ConstraintValidatorContext context) {
    // https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/validator-customconstraints.html#validator-customconstraints-validator
    return false;
  }
}
