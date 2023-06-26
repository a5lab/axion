package com.a5lab.axion.domain.radar.approvers;


import lombok.RequiredArgsConstructor;

import com.a5lab.axion.domain.radar.Radar;

@RequiredArgsConstructor
public class SegmentApprover {

  private final Radar radar;

  public void approve() throws InvalidSegmentException {
    // throw new InvalidSegmentException("Invalid segment exceptions.");
  }
}
