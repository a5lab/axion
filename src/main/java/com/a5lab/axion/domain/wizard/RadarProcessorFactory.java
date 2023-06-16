package com.a5lab.axion.domain.wizard;

public class RadarProcessorFactory {
  public RadarProcessor create() {
    return new TechnologyRadarProcessor();
  }
}
