package com.a5lab.axion.domain.radar;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;

@RequiredArgsConstructor
public class SegmentNumberSaveApprover implements ModelApprover {

  private static final int SEGMENT_NUMBER = 4;

  private final MessageSource messageSource;

  private final Optional<Radar> radarOptional;


  @Override
  public List<ModelError> approve() {
    if (radarOptional == null || radarOptional.isEmpty() || radarOptional.get().getSegmentList() == null
        || radarOptional.get().getSegmentList().size() != SEGMENT_NUMBER) {
      return List.of(new ModelError("unable_to_save_due_to_segment_number",
          messageSource.getMessage("radar.error.unable_to_save_due_to_segment_number", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();

  }
}
