package com.a5lab.axion.domain.wizard;


import org.springframework.context.ApplicationContext;

public class ProcessRadarProcessor extends AbstractRadarProcessor {

  public ProcessRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    super(applicationContext, wizardDto);
  }

  @Override
  public void process() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
