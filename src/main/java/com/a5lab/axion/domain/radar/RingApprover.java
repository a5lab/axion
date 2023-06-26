package com.a5lab.axion.domain.radar;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RingApprover {

  private final Radar radar;

  public void approve() throws InvalidRingException {
    // throw new InvalidRingException("Invalid ring exceptions.");
  }
}
