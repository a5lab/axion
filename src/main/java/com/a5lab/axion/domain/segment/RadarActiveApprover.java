package com.a5lab.axion.domain.segment;


import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.InconsistentModelException;
import com.a5lab.axion.domain.ModelApprover;

@RequiredArgsConstructor
public class RadarActiveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Segment segment;

  @Override
  public void approve() throws InconsistentModelException {
    if (segment.getRadar().isActive()) {
      throw new InconsistentModelException("error_to_delete_due_to_active",
          messageSource.getMessage("segment.flash.error.active_radar", null, LocaleContextHolder.getLocale()));
    }
  }
}
