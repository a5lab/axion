package com.a5lab.axion.domain.radar;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

// @RequiredArgsConstructor
// @Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)

@Slf4j
@Component
@Scope("request")
public class RadarPrimaryValidator
    implements ConstraintValidator<RadarPrimaryConstraint, Radar> {

  // @Autowired
  // private final RadarRepository radarRepository;

  private RadarService radarService;

  @Autowired
  public RadarPrimaryValidator(RadarService aService) {
    radarService = aService;
  }


  @Override
  public boolean isValid(Radar radar, ConstraintValidatorContext context) {
    //
    System.out.println(radarService.toString());
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
