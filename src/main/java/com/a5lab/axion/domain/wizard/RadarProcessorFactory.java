package com.a5lab.axion.domain.wizard;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;

public class RadarProcessorFactory {
  public RadarProcessor create(RadarTypeDto radarType) {
    switch (radarType.getCode()) {
      case RadarType.CAPABILITY_RADAR:
        return new CapabilityRadarProcessor();
      case RadarType.PRACTICE_RADAR:
        return new PracticeRadarProcessor();
      case RadarType.PROCESS_RADAR:
        return new ProcessRadarProcessor();
      case RadarType.TECHNOLOGY_RADAR:
        return new TechnologyRadarProcessor();
      default:
        throw new IllegalArgumentException("Unknown radars type: " + radarType.getCode());
    }
  }
}
