package com.a5lab.tabr.domain.radars;


public class RadarPrimaryValidatorBase {

  // private final RadarService radarService;

  boolean isPrimaryValid(Long id, boolean primary) {
    // Can be only one primary
    if (primary == false) {
      return true;
    }

    /*
    // TODO: just to check, doesnt work.
    if (id != null) {
      Optional<RadarDto> radarDtoOptional =  this.radarService.findById(id);
    }

     */
    return false;
  }

}
