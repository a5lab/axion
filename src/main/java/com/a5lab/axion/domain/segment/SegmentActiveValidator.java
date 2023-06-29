package com.a5lab.axion.domain.segment;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentActiveValidator
    implements ConstraintValidator<SegmentActiveConstraint, Segment> {

  @Override
  public boolean isValid(Segment segment, ConstraintValidatorContext context) {
    /*
    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
    */
    return true;
  }
}
