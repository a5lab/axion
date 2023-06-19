package com.a5lab.axion.domain.wizard;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.wizard.processors.RadarProcessor;
import com.a5lab.axion.domain.wizard.processors.RadarProcessorFactory;

@RequiredArgsConstructor
@Service
@Transactional
public class WizardServiceImpl implements WizardService {
  @Autowired
  private ApplicationContext applicationContext;

  @Override
  @Transactional
  public void createRadarEnv(WizardDto wizardDto) throws Exception {
    RadarProcessorFactory radarProcessorFactory = new RadarProcessorFactory();
    RadarProcessor radarProcessor = radarProcessorFactory.create(applicationContext, wizardDto);
    radarProcessor.process();
  }
}
