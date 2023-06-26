package com.a5lab.axion.domain.radar.approvers;

public class InvalidPrimaryException extends RuntimeException {
  public InvalidPrimaryException(String errorMessage) {
    super(errorMessage);
  }

  public InvalidPrimaryException(String errorMessage, Throwable err) {
    super(errorMessage, err);
  }
}
