package com.a5lab.axion.domain.radar.approvers;


import lombok.RequiredArgsConstructor;

import com.a5lab.axion.domain.radar.Radar;

@RequiredArgsConstructor
public class RingApprover {

  private final Radar radar;

  public void approve() throws InvalidRingException {
    // throw new InvalidRingException("Invalid ring exceptions.");
  }
}
