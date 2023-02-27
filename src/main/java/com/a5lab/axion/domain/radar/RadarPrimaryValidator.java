package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.a5lab.axion.config.ApplicationContextProvider;

public class RadarPrimaryValidator
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  private RadarService radarService;

  @Override
  public void initialize(RadarPrimaryConstraint constraintAnnotation) {
    initRadarService();
  }

  private void initRadarService() {
    if (radarService == null) {
      radarService = ApplicationContextProvider
          .getApplicationContext().getBean(RadarService.class);
    }
  }

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    initRadarService();
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
