package com.a5lab.axion.domain.wizard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;

@RequiredArgsConstructor
@Service
@Transactional
public class WizardServiceImpl implements WizardService {

  public final RadarService radarService;

  public final RingService ringService;

  public final SegmentService segmentService;

  public final TechnologyService technologyService;
  public final TechnologyBlipService technologyBlipService;

  @Override
  @Transactional
  public void createRadarEnv(WizardDto wizardDto) throws Exception {
    RadarProcessorFactory radarProcessorFactory = new RadarProcessorFactory();
    RadarProcessor radarProcessor = radarProcessorFactory.create(this, wizardDto);
    radarProcessor.process();
  }
}
