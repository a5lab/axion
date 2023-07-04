package com.a5lab.axion.domain.segment;


import java.util.LinkedList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;

@RequiredArgsConstructor
public class RadarActiveDeleteApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Segment segment;

  @Override
  public List<ModelError> approve() throws ValidationException {
    if (segment.getRadar().isActive()) {
      return List.of(new ModelError("unable_to_delete_active_radar",
          messageSource.getMessage("segment.error.unable_to_delete_active_radar", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();
  }
}
