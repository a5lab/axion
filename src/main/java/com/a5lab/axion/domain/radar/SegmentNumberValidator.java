package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentNumberValidator
    implements ConstraintValidator<SegmentNumberConstraint, Radar> {

  private static final int SEGMENT_NUBMER = 4;

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    /*
    if (radar.isActive()) {
      if (radar.getSegmentList() != null) {
        return radar.getSegmentList().size() == SEGMENT_NUBMER;
      }
      return false;
    }*/
    return true;
  }
}
