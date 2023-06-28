package com.a5lab.axion.domain.radar;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SegmentsNumberValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SegmentsNumberConstraint {
  String message() default "must be only four segments";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}