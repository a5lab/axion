package com.a5lab.axion.domain.ring;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = RingUppercaseTitleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RingUppercaseTitleConstraint {
  String message() default "should be uppercase";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}