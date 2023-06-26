package com.a5lab.axion.domain;

import lombok.Getter;

@Getter
public class InconsistentModelException extends RuntimeException {
  private final String errorCode;

  private final String field;

  public InconsistentModelException(String errorCode, String errorMessage, String field) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.field = field;
  }

  public InconsistentModelException(String errorCode, String errorMessage) {
    super(errorMessage);
    this.errorCode = errorCode;
    this.field = null;
  }
}
