package com.a5lab.axion.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.metadata.ConstraintDescriptor;

import lombok.AllArgsConstructor;

/**
 * Useful links:
 * https://stackoverflow.com/questions/22938407/bean-validation-how-can-i-manually-create-a-constraintviolation
 */
@AllArgsConstructor
public class ConstraintViolationImpl<T> implements ConstraintViolation<T> {

  private final String interpolatedMessage;

  private final Path propertyPath;

  public String getMessage() {
    return "";
  }

  public String getMessageTemplate() {
    return "";
  }

  public T getRootBean() {
    return null;
  }

  public Class<T> getRootBeanClass() {
    return null;
  }

  public Object getLeafBean() {
    return null;
  }

  public Object[] getExecutableParameters() {
    return null;
  }

  public Object getExecutableReturnValue() {
    return null;
  }

  public Path getPropertyPath() {
    return null;
  }

  public Object getInvalidValue() {
    return null;
  }

  public ConstraintDescriptor<?> getConstraintDescriptor() {
    return null;
  }

  public <U> U unwrap(Class<U> var1) {
    return null;
  }
}
