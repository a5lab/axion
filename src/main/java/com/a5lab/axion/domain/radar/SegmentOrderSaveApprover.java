package com.a5lab.axion.domain.radar;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.segment.Segment;

@RequiredArgsConstructor
public class SegmentOrderSaveApprover implements ModelApprover {

  private static final int SEGMENT_NUMBER = 4;

  private final MessageSource messageSource;

  private final Optional<Radar> radarOptional;


  @Override
  public List<ModelError> approve() {
    int[] positionArray = new int[]{ 1, 1, 1, 1};
    if (radarOptional.isPresent()) {
      for (Segment segment : radarOptional.get().getSegmentList()) {
        if (segment.getPosition() < positionArray.length) {
          positionArray[segment.getPosition()] = 0;
        }
      }
    }

    if (Arrays.stream(positionArray).sum() != 0) {
      return List.of(new ModelError("unable_to_save_due_to_segment_order",
          messageSource.getMessage("radar.error.unable_to_save_due_to_segment_order", null,
              LocaleContextHolder.getLocale()), null));
    }
    return new LinkedList<>();
  }
}
