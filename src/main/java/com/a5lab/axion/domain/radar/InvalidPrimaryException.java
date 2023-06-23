package com.a5lab.axion.domain.radar;

public class InvalidPrimaryException extends RuntimeException {
  public InvalidPrimaryException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
