package com.a5lab.axion.domain.wizard;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.a5lab.axion.domain.AbstractPojoTests;


class RadarProcessorFactoryTests  extends AbstractPojoTests {

  @Test
  void createTechnologyRadarProcessor() {
    RadarProcessorFactory radarProcessorFactory = new RadarProcessorFactory();
    RadarProcessor radarProcessor = radarProcessorFactory.create();
    Assertions.assertInstanceOf(TechnologyRadarProcessor.class, radarProcessor);
  }

}