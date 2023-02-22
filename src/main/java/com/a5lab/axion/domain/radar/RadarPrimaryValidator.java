package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class RadarPrimaryValidator
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {
  public RadarPrimaryValidator() {
    radarService = ApplicationContextProvider
            .getApplicationContext().getBean(RadarService.class);
  }

  private RadarService radarService;
  @Autowired
  public void setRadarService(RadarService radarService) {
    this.radarService = radarService;
  }

  // private final RadarRepository radarRepository;

  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {

    Assert.notNull(radarService);
    //
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
