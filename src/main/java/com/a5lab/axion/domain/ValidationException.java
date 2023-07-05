package com.a5lab.axion.domain;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
  private final List<ModelError> modelErrorList = new LinkedList<>();

  public ValidationException(String errorMessage, List<ModelError> modelErrorList) {
    super(errorMessage);
    this.modelErrorList.addAll(modelErrorList);
  }

  public static String buildErrorMessage(List<ModelError> modelErrorList) {
    StringBuilder stringBuilder = new StringBuilder();
    for (ModelError modelError : modelErrorList) {
      stringBuilder.append(modelError.getErrorMessage());
    }
    return stringBuilder.toString();
  }
}
