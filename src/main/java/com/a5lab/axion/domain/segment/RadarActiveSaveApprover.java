package com.a5lab.axion.domain.segment;


import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar.Radar;

@RequiredArgsConstructor
public class RadarActiveSaveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Radar radar;

  private final Optional<Segment> segmentOptional;

  @Override
  public List<ModelError> approve() throws ValidationException {
    if (radar.isActive() || segmentOptional.isPresent() && radar.getId() != segmentOptional.get().getRadar().getId()
        && segmentOptional.get().getRadar().isActive()) {
      return List.of(new ModelError("unable_to_save_due_to_active_radar",
          messageSource.getMessage("segment.error.unable_to_save_due_to_active_radar", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();
  }
}
