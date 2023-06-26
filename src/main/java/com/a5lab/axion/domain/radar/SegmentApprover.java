package com.a5lab.axion.domain.radar;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SegmentApprover {

  private final Radar radar;

  public void approve() throws InvalidSegmentException {
    // throw new InvalidSegmentException("Invalid segment exceptions.");
  }
}
