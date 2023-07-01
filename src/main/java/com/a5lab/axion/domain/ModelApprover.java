package com.a5lab.axion.domain;

import jakarta.validation.ConstraintViolationException;

public interface ModelApprover {
  void approve() throws ConstraintViolationException;
}
