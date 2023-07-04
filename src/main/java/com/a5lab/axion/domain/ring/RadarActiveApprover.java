package com.a5lab.axion.domain.ring;


import java.util.LinkedList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;

@RequiredArgsConstructor
public class RadarActiveApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final Ring ring;

  @Override
  public List<ModelError> approve() throws ValidationException {
    if (ring.getRadar().isActive()) {
      return List.of(new ModelError("error_to_delete_due_to_active",
          messageSource.getMessage("ring.flash.error.active_radar", null, LocaleContextHolder.getLocale()),
          "primary"));
    }
    return new LinkedList<>();
  }
}
