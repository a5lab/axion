package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarPrimaryValidator implements ConstraintValidator<RadarPrimaryConstraint, Boolean> {

  @Override
  public boolean isValid(Boolean value, ConstraintValidatorContext context) {
    // Always true for not primary
    if (value == false) {
      return true;
    }
    // ?! How to get id here? Should I use class level constraints?
    return false;
  }

}
