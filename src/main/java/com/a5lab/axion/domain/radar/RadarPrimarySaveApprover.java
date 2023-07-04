package com.a5lab.axion.domain.radar;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;

@RequiredArgsConstructor
public class RadarPrimarySaveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final RadarDto radarDto;

  private final Radar radar;


  @Override
  public List<ModelError> approve() {
    if (!Objects.equals(radarDto.getId(), radar.getId()) && radar.isPrimary()) {
      return List.of(new ModelError("primary_invalid_primary",
          messageSource.getMessage("radar.error.invalid_primary", null, LocaleContextHolder.getLocale()),
          "primary"));
    }
    return new LinkedList<>();
  }
}
