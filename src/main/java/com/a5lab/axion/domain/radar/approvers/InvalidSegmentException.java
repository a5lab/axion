package com.a5lab.axion.domain.radar.approvers;

public class InvalidSegmentException extends RuntimeException {
  public InvalidSegmentException(String errorMessage) {
    super(errorMessage);
  }
}
