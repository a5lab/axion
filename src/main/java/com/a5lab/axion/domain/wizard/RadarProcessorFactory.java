package com.a5lab.axion.domain.wizard;

import com.a5lab.axion.domain.radar_type.RadarType;

public class RadarProcessorFactory {
  public RadarProcessor create(WizardServiceImpl wizardService, WizardDto wizardDto) {
    switch (wizardDto.getRadarType().getCode()) {
      case RadarType.CAPABILITY_RADAR:
        return new CapabilityRadarProcessor(wizardService, wizardDto);
      case RadarType.PRACTICE_RADAR:
        return new PracticeRadarProcessor(wizardService, wizardDto);
      case RadarType.PROCESS_RADAR:
        return new ProcessRadarProcessor(wizardService, wizardDto);
      case RadarType.TECHNOLOGY_RADAR:
        return new TechnologyRadarProcessor(wizardService, wizardDto);
      default:
        throw new IllegalArgumentException("Unknown radars type: " + wizardDto.getRadarType().getCode());
    }
  }
}
