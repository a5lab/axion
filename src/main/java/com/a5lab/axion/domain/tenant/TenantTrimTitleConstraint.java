package com.a5lab.axion.domain.tenant;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = TenantTitleTrimValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantTrimTitleConstraint {
  String message() default "should be without whitespaces before and after";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}