package com.a5lab.tabr.domain.tenants;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = TenantTitleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TenantTitleConstraint {
  String message() default "Invalid Tenant Title";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}