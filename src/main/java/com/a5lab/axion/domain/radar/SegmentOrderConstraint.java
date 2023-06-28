package com.a5lab.axion.domain.radar;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = SegmentOrderValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SegmentOrderConstraint {
  String message() default "segments must be consecutively numbered starting from 0";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}