package com.a5lab.axion.domain.radar;


import java.util.Objects;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PrimaryApprover {

  private final RadarDto radarDto;

  private final Radar radar;


  public void approve() throws InvalidPrimaryException {
    if (!Objects.equals(radarDto.getId(), radar.getId()) && radar.isPrimary()) {
      throw new InvalidPrimaryException("Should be only one primary radar");
    }
  }
}
