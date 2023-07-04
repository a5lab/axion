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
public class RadarUniqueTitleSaveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final RadarDto radarDto;

  private final Radar radar;


  @Override
  public List<ModelError> approve() {
    if (!Objects.equals(radarDto.getId(), radar.getId())) {
      return List.of(new ModelError("title_is_taken",
          messageSource.getMessage("radar.error.title_is_taken", null, LocaleContextHolder.getLocale()),
          "title"));
    }
    return new LinkedList<>();
  }
}
