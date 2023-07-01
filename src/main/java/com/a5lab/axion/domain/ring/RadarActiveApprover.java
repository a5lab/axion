package com.a5lab.axion.domain.ring;


import jakarta.validation.ConstraintViolationException;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;

import com.a5lab.axion.domain.ModelApprover;

@RequiredArgsConstructor
public class RadarActiveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Ring ring;

  @Override
  public void approve() throws ConstraintViolationException {
    if (ring.getRadar().isActive()) {
      System.out.println("implement me");
      /* fuck
      throw new InconsistentModelException("error_to_delete_due_to_active",
          messageSource.getMessage("ring.flash.error.active_radar", null, LocaleContextHolder.getLocale()));
       */
    }
  }
}
