package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarType;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

public class WizardServiceTest extends AbstractServiceTests {
  private final RadarService radarService = Mockito.mock(RadarService.class);
  private final RingService ringService = Mockito.mock(RingService.class);
  private final SegmentService segmentService = Mockito.mock(SegmentService.class);
  private final TechnologyService technologyService = Mockito.mock(TechnologyService.class);
  private final TechnologyBlipService technologyBlipService = Mockito.mock(TechnologyBlipService.class);
  private final WizardService wizardService =
      new WizardServiceImpl(radarService, ringService, segmentService, technologyService, technologyBlipService);


  @Test
  void shouldCreateRadarEnv() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);

    final WizardDto wizardDto = new WizardDto(radarTypeDto);

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(3L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("Technology radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(4L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("Languages");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(5L);
    ringDto.setRadar(null);
    ringDto.setTitle("ADOPT");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
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
    technologyBlipDto.setRadar(radarDto);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

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
    Mockito.verify(technologyService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(technologyBlipService, Mockito.times(2)).save(any());
  }
}
