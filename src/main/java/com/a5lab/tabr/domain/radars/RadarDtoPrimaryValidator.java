package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarDtoPrimaryValidator
    implements ConstraintValidator<RadarDtoPrimaryConstraint, RadarDto> {

  @Override
  public boolean isValid(RadarDto radarDto, ConstraintValidatorContext context) {
    // TODO: implement it
    if (radarDto.isPrimary() == false) {
      return true;
    }

    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
  }
}
