package com.a5lab.axion.domain.segment;


import jakarta.validation.ConstraintViolationException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import com.a5lab.axion.domain.ModelApprover;

@RequiredArgsConstructor
public class RadarActiveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Segment segment;

  @Override
  public void approve() throws ConstraintViolationException {
    if (segment.getRadar().isActive()) {
      System.out.println("implement me");
      /* fuck
      throw new InconsistentModelException("error_to_delete_due_to_active",
          messageSource.getMessage("segment.flash.error.active_radar", null, LocaleContextHolder.getLocale()));

       */
    }
  }
}
