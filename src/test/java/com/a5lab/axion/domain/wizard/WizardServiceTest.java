package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.Radar;
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
  private final RingMapper ringMapper = Mappers.getMapper(RingMapper.class);
  private final SegmentMapper segmentMapper = Mappers.getMapper(SegmentMapper.class);
  private final TechnologyMapper technologyMapper = Mappers.getMapper(TechnologyMapper.class);
  private final WizardService wizardService =
      new WizardServiceImpl(radarService, ringService, segmentService, technologyService, technologyBlipService);


  @Test
  void shouldCreateRadarEnv() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final Wizard wizard = new Wizard(radarType);

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(2L);
    radarDto.setRadarType(radarType);

    final Radar radar = new Radar();
    radar.setId(3L);
    radar.setTitle("Technology radar");
    radar.setDescription("My radar description");

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
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    Mockito.when(radarService.save(any(Radar.class))).thenReturn(radar);
    Mockito.when(ringService.save(any(RingDto.class))).thenReturn(ringDto);
    Mockito.when(ringService.findByTitle(any())).thenReturn(Optional.of(ringMapper.toEntity(ringDto)));
    Mockito.when(segmentService.save(any(SegmentDto.class))).thenReturn(segmentDto);
    Mockito.when(segmentService.findByTitle(any())).thenReturn(Optional.of(segmentMapper.toEntity(segmentDto)));
    Mockito.when(technologyService.findByTitle(any()))
        .thenReturn(Optional.of(technologyMapper.toEntity(technologyDto)));
    Mockito.when(technologyBlipService.save(any(TechnologyBlipDto.class))).thenReturn(technologyBlipDto);

    wizardService.createRadarEnv(wizard);

    Mockito.verify(radarService, Mockito.times(2)).save(any(Radar.class));
    Mockito.verify(ringService, Mockito.times(4)).save(any(RingDto.class));
    Mockito.verify(ringService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(segmentService, Mockito.times(4)).save(any(SegmentDto.class));
    Mockito.verify(segmentService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(technologyService, Mockito.times(2)).findByTitle(any());
    Mockito.verify(technologyBlipService, Mockito.times(2)).save(any(TechnologyBlip.class));
  }
}
