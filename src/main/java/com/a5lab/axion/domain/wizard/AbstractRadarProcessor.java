package com.a5lab.axion.domain.wizard;

import org.springframework.context.ApplicationContext;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

public abstract class AbstractRadarProcessor implements RadarProcessor {

  protected final ApplicationContext applicationContext;

  protected final RadarTypeRepository radarTypeRepository;

  protected final RadarRepository radarRepository;

  protected final WizardDto wizardDto;

  protected Radar radar = new Radar();


  public AbstractRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    this.applicationContext = applicationContext;
    this.wizardDto = wizardDto;

    // Create repositories based on application context
    radarTypeRepository =  applicationContext.getBean(RadarTypeRepository.class);
    radarRepository =  applicationContext.getBean(RadarRepository.class);
  }
}
