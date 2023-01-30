package com.a5lab.tabr.domain.radars;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RadarDtoPrimaryValidator
    implements ConstraintValidator<RadarDtoPrimaryConstraint, RadarDto> {

  private final RadarService radarService;

  @Override
  public boolean isValid(RadarDto radarDto, ConstraintValidatorContext context) {
    // TODO: implement it (base class?)
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
