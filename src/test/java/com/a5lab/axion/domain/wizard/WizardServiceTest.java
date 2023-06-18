package com.a5lab.axion.domain.wizard;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;

public class WizardServiceTest extends AbstractServiceTests {
  @MockBean
  private RadarService radarService;
  @MockBean
  private RingService ringService;
  @MockBean
  private SegmentService segmentService;
  @MockBean
  private TechnologyService technologyService;
  @MockBean
  private TechnologyBlipService technologyBlipService;
  @Autowired
  private WizardService wizardService;

  @Test
  void shouldCreateCapabilityRadarEnv() {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setCode(RadarType.CAPABILITY_RADAR);

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarTypeDto.getId());
    wizardDto.setRadarTypeCode(radarTypeDto.getCode());

    UnsupportedOperationException exception =
        catchThrowableOfType(() -> wizardService.createRadarEnv(wizardDto),
            UnsupportedOperationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
  }

  @Test
  void shouldCreatePracticeRadarEnv() {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setCode(RadarType.PRACTICE_RADAR);

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarTypeDto.getId());
    wizardDto.setRadarTypeCode(radarTypeDto.getCode());

    UnsupportedOperationException exception =
        catchThrowableOfType(() -> wizardService.createRadarEnv(wizardDto),
            UnsupportedOperationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
  }

  @Test
  void shouldCreateProcessRadarEnv() {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setCode(RadarType.PROCESS_RADAR);

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarTypeDto.getId());
    wizardDto.setRadarTypeCode(radarTypeDto.getCode());

    UnsupportedOperationException exception =
        catchThrowableOfType(() -> wizardService.createRadarEnv(wizardDto),
            UnsupportedOperationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
  }


  @Test
  void shouldCreateTechnologyRadarEnv() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarTypeDto.getId());
    wizardDto.setRadarTypeCode(radarTypeDto.getCode());

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(3L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("Technology radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(4L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setRadarTitle(radarDto.getTitle());
    segmentDto.setTitle("Languages");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(5L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setRadarTitle(radarDto.getTitle());
    ringDto.setTitle("ADOPT");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(6L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(7L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.when(radarService.save(any())).thenReturn(radarDto);
    Mockito.when(ringService.save(any())).thenReturn(ringDto);
    Mockito.when(ringService.findByTitle(any())).thenReturn(Optional.of(ringDto));
    Mockito.when(segmentService.save(any())).thenReturn(segmentDto);
    Mockito.when(segmentService.findByTitle(any())).thenReturn(Optional.of(segmentDto));
    Mockito.when(technologyService.findByTitle(any())).thenReturn(Optional.of(technologyDto));
    Mockito.when(technologyBlipService.save(any())).thenReturn(technologyBlipDto);

    wizardService.createRadarEnv(wizardDto);

    Mockito.verify(radarService, Mockito.times(2)).save(any());
    Mockito.verify(ringService, Mockito.times(4)).save(any());
    Mockito.verify(ringService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(segmentService, Mockito.times(4)).save(any());
    Mockito.verify(segmentService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(technologyService, Mockito.times(4)).findByTitle(any());
    Mockito.verify(technologyBlipService, Mockito.times(2)).save(any());
  }
}
