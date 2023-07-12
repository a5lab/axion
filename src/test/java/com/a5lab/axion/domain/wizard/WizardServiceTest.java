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
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipRepository;

public class WizardServiceTest extends AbstractServiceTests {

  @MockBean
  private RadarTypeRepository radarTypeRepository;
  @MockBean
  private RadarRepository radarRepository;
  @MockBean
  private RingRepository ringRepository;
  @MockBean
  private SegmentRepository segmentRepository;
  @MockBean
  private TechnologyRepository technologyRepository;
  @MockBean
  private TechnologyBlipRepository technologyBlipRepository;
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
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarType.getId());
    wizardDto.setRadarTypeCode(radarType.getCode());

    final Radar radar = new Radar();
    radar.setId(3L);
    radar.setRadarType(radarType);
    radar.setTitle("Technology radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(4L);
    segment.setRadar(radar);
    segment.setTitle("Languages");
    segment.setDescription("My segment description");
    segment.setPosition(1);

    final Ring ring = new Ring();
    ring.setId(5L);
    ring.setRadar(radar);
    ring.setTitle("ADOPT");
    ring.setDescription("My ring description");
    ring.setPosition(1);
    ring.setColor("#fbdb84");

    final Technology technology = new Technology();
    technology.setId(6L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(7L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setSegment(segment);
    technologyBlip.setTechnology(technology);

    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));
    Mockito.when(radarRepository.save(any())).thenReturn(radar);
    Mockito.when(ringRepository.findByTitle(any())).thenReturn(Optional.of(ring));
    Mockito.when(segmentRepository.findByTitle(any())).thenReturn(Optional.of(segment));
    Mockito.when(technologyRepository.findByTitle(any())).thenReturn(Optional.of(technology));
    Mockito.when(technologyBlipRepository.save(any())).thenReturn(technologyBlip);

    wizardService.createRadarEnv(wizardDto);

    Mockito.verify(radarTypeRepository).findById(any());
    Mockito.verify(radarRepository).save(any());
    Mockito.verify(ringRepository, Mockito.times(2)).findByTitle(any());
    Mockito.verify(segmentRepository, Mockito.times(2)).findByTitle(any());
    Mockito.verify(technologyRepository, Mockito.times(4)).findByTitle(any());
    Mockito.verify(technologyBlipRepository, Mockito.times(2)).save(any());
  }
}
