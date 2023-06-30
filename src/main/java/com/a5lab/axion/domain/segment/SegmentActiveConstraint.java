package com.a5lab.axion.domain.segment;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SegmentActiveValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SegmentActiveConstraint {
  String message() default "active radar can't be modified";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}