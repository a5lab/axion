package com.a5lab.axion.domain;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private final List<ModelError> modelErrorList = new LinkedList<>();


  public ValidationException(List<ModelError> modelErrorList) {
    super("Validation error is occurred");
    this.modelErrorList.addAll(modelErrorList);
  }
}
