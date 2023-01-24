package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarPrimaryValidator extends RadarPrimaryValidatorBase
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    if (this.isPrimaryValid(radar.getId(), radar.isPrimary())) {
      return true;
    }

    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
  }
}
