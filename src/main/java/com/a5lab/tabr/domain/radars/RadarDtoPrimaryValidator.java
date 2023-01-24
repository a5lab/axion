package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RadarDtoPrimaryValidator extends RadarPrimaryValidatorBase
    implements ConstraintValidator<RadarDtoPrimaryConstraint, RadarDto> {

  @Override
  public boolean isValid(RadarDto radarDto, ConstraintValidatorContext context) {
    if (this.isPrimaryValid(radarDto.getId(), radarDto.isPrimary())) {
      return true;
    }

    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
  }
}
