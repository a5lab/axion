package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RadarPrimaryValidator
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  private final RadarService radarService;
  // private final RadarRepository radarRepository;

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    //
    return true;
    /*
    // TODO: implement it
    if (radars.isPrimary() == false) {
      return true;
    }

    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;

     */
  }
}
