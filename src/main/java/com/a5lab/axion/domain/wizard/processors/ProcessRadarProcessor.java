package com.a5lab.axion.domain.wizard.processors;


import org.springframework.context.ApplicationContext;

import com.a5lab.axion.domain.wizard.WizardDto;

public class ProcessRadarProcessor extends AbstractRadarProcessor {

  public ProcessRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    super(applicationContext, wizardDto);
  }

  @Override
  public void process() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
