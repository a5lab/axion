package com.a5lab.axion.domain.radar;


import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.InconsistentModelException;
import com.a5lab.axion.domain.ModelApprover;

@RequiredArgsConstructor
public class RadarPrimaryApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final RadarDto radarDto;

  private final Radar radar;


  @Override
  public void approve() throws InconsistentModelException {
    if (!Objects.equals(radarDto.getId(), radar.getId()) && radar.isPrimary()) {
      throw new InconsistentModelException("primary_invalid_primary",
          messageSource.getMessage("radar.form.error.invalid_primary", null, LocaleContextHolder.getLocale()),
          "primary");
    }
  }
}
