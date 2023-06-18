package com.a5lab.axion.domain.wizard.processors;

import org.springframework.context.ApplicationContext;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.wizard.WizardDto;

public class RadarProcessorFactory {
  public RadarProcessor create(ApplicationContext applicationContext, WizardDto wizardDto) {
    return switch (wizardDto.getRadarTypeCode()) {
      case RadarType.CAPABILITY_RADAR -> new CapabilityRadarProcessor(applicationContext, wizardDto);
      case RadarType.PRACTICE_RADAR -> new PracticeRadarProcessor(applicationContext, wizardDto);
      case RadarType.PROCESS_RADAR -> new ProcessRadarProcessor(applicationContext, wizardDto);
      case RadarType.TECHNOLOGY_RADAR -> new TechnologyRadarProcessor(applicationContext, wizardDto);
      default -> throw new IllegalArgumentException("Unknown radars type: " + wizardDto.getRadarTypeCode());
    };
  }
}
