package com.a5lab.axion.domain.wizard;

import com.a5lab.axion.domain.radar.WizardDto;

public interface WizardService {
  void createRadarEnv(WizardDto wizardDto) throws Exception;
}
