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
public class RingOrderSaveApprover implements ModelApprover {

  private static final int RING_NUBMER = 4;

  private final MessageSource messageSource;

  private final RadarDto radarDto;

  private final Radar radar;


  @Override
  public List<ModelError> approve() {
    /*
      String message() default "rings must be consecutively numbered starting from 0";
    }*/

    if (!Objects.equals(radarDto.getId(), radar.getId()) && radar.isPrimary()) {
      return List.of(new ModelError("primary_invalid_primary",
          messageSource.getMessage("radar.error.invalid_primary", null, LocaleContextHolder.getLocale()),
          "primary"));
    }
    return new LinkedList<>();
  }
}
