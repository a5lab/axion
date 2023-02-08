package com.a5lab.tabr.domain.wizard;

import com.a5lab.tabr.domain.radars.Radar;

public interface WizardService {
  void createRadar(Radar radar) throws Exception;
}
