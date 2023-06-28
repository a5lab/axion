package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RingNumberValidator
    implements ConstraintValidator<RingNumberConstraint, Radar> {

  private static final int RING_NUBMER = 4;


  @Override
  /*
    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
  */
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    if(radar.isActive()) {
      if(radar.getRingList() != null){
        return radar.getRingList().size() == RING_NUBMER;
      }
      return false;
    }
    return true;
  }
}
