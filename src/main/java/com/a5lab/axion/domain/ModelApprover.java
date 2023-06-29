package com.a5lab.axion.domain;

public interface ModelApprover {
  void approve() throws InconsistentModelException;
}
