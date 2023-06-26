package com.a5lab.axion.domain.radar.approvers;


import java.util.Objects;

import lombok.RequiredArgsConstructor;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarDto;

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
