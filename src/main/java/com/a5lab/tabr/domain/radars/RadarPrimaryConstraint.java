package com.a5lab.tabr.domain.radars;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RadarPrimaryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RadarPrimaryConstraint {
  String message() default "should be only one primary";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}