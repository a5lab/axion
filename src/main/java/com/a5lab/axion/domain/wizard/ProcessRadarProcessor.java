package com.a5lab.axion.domain.wizard;


public class ProcessRadarProcessor extends AbstractRadarProcessor {

  public ProcessRadarProcessor(WizardServiceImpl wizardService, WizardDto wizardDto) {
    super(wizardService, wizardDto);
  }

  @Override
  public void process() throws Exception {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
