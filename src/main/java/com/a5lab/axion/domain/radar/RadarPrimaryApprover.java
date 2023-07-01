package com.a5lab.axion.domain.radar;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ConstraintViolationImpl;
import com.a5lab.axion.domain.ModelApprover;
import com.a5lab.axion.domain.PathImpl;

@RequiredArgsConstructor
public class RadarPrimaryApprover implements ModelApprover {

  private final MessageSource messageSource;

  private final RadarDto radarDto;

  private final Radar radar;


  @Override
  public void approve() throws ConstraintViolationException {
    if (!Objects.equals(radarDto.getId(), radar.getId()) && radar.isPrimary()) {
      ConstraintViolation<Radar> constraintViolation = new ConstraintViolationImpl<Radar>(
          messageSource.getMessage("radar.form.error.invalid_primary", null, LocaleContextHolder.getLocale()),
          new PathImpl("primary"));
      Set<ConstraintViolation<Radar>> constraintViolationSet = new HashSet<>();
      constraintViolationSet.add(constraintViolation);
      throw new ConstraintViolationException(constraintViolationSet);

      /*
      throw new InconsistentModelException("primary_invalid_primary",
          messageSource.getMessage("radar.form.error.invalid_primary", null, LocaleContextHolder.getLocale()),
          "primary");
       */
    }
  }
}
