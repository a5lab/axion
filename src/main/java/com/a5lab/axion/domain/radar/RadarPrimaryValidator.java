package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.stereotype.Component;

import com.a5lab.axion.config.ApplicationContextProvider;

@Component
public class RadarPrimaryValidator
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  private RadarService radarService;

  @Override
  public void initialize(RadarPrimaryConstraint constraintAnnotation) {
    radarService = ApplicationContextProvider.getBean(RadarService.class);
  }

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    System.out.println(radarService.findAll().toString());
    return true;
    /*
    // TODO: implement it
    if (radars.isPrimary() == false) {
      return true;
    }

    // Attach error to primary fields
    context.disableDefaultConstraintViolation();
    context.buildConstraintViolationWithTemplate("can be only one primary")
        .addPropertyNode("primary").addConstraintViolation();
    return false;
     */
  }
}
