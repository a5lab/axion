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
public class RingNumberSaveApprover implements ModelApprover {

  private static final int RING_NUMBER = 4;

  private final MessageSource messageSource;

  private final Optional<Radar> radarOptional;


  @Override
  public List<ModelError> approve() {
    if (radarOptional.isEmpty() || radarOptional.get().getRingList() == null
        || radarOptional.get().getRingList().size() != RING_NUMBER) {
      return List.of(new ModelError("unable_to_save_due_to_ring_number",
          messageSource.getMessage("radar.error.unable_to_save_due_to_ring_number", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();
  }
}
