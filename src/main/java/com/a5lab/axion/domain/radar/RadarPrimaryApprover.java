package com.a5lab.axion.domain.radar;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.a5lab.axion.domain.ConstraintViolationImpl;
import com.a5lab.axion.domain.ModelApprover;

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
          PathImpl.createPathFromString("primary"));
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
