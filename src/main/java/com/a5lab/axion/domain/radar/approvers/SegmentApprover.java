package com.a5lab.axion.domain.radar.approvers;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import com.a5lab.axion.domain.InconsistentModelException;
import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.radar.Radar;

@RequiredArgsConstructor
public class SegmentApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Radar radar;

  @Override
  public void approve() throws InconsistentModelException {
    // throw new InvalidSegmentException("Invalid segment exceptions.");
  }
}
