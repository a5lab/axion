package com.a5lab.axion.domain.radar.approvers;

public class InvalidRingException extends RuntimeException {
  public InvalidRingException(String errorMessage) {
    super(errorMessage);
  }
}
