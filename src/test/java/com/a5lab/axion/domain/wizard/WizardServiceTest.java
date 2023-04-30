package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.ring.RingServiceImpl;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.segment.SegmentServiceImpl;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology.TechnologyServiceImpl;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapperImpl;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipServiceImpl;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarMapper;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar.RadarServiceImpl;
import com.a5lab.axion.domain.radar_type.RadarType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;

public class WizardServiceTest extends AbstractServiceTests {


  private final RadarRepository radarRepository = Mockito.mock(RadarRepository.class);

  private final RadarMapper radarMapper = Mappers.getMapper(RadarMapper.class);

  private final RadarService radarService = new RadarServiceImpl(radarRepository, radarMapper);

  private final RingRepository ringRepository = Mockito.mock(RingRepository.class);

  private final RingMapper ringMapper = Mappers.getMapper(RingMapper.class);

  private final RingService ringService = new RingServiceImpl(ringRepository, ringMapper);

  private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);

  private final SegmentMapper segmentMapper = Mappers.getMapper(SegmentMapper.class);

  private final SegmentService segmentService = new SegmentServiceImpl(segmentRepository, segmentMapper);

  private final TechnologyRepository technologyRepository = Mockito.mock(TechnologyRepository.class);

  private final TechnologyMapper technologyMapper = Mappers.getMapper(TechnologyMapper.class);

  private final TechnologyService technologyService = new TechnologyServiceImpl(technologyRepository, technologyMapper);

  private final TechnologyBlipRepository technologyBlipRepository = Mockito.mock(TechnologyBlipRepository.class);

  private final TechnologyBlipMapper technologyBlipMapper =
      new TechnologyBlipMapperImpl(ringMapper, segmentMapper, technologyMapper);

  private final TechnologyBlipService technologyBlipService =
      new TechnologyBlipServiceImpl(technologyBlipRepository, technologyBlipMapper);

  private final WizardService wizardService = new WizardServiceImpl(radarService, ringService, segmentService, technologyService, technologyBlipService);


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
    radar.setId(10L);
    radar.setTitle("Technology radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("Languages");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("ADOPT");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    Mockito.when(radarService.save(any(Radar.class))).thenReturn(radar);
    Mockito.when(ringService.save(any(RingDto.class))).thenReturn(ringDto);
    Mockito.when(segmentService.save(any(SegmentDto.class))).thenReturn(segmentDto);
    Mockito.when(technologyService.save(any(TechnologyDto.class))).thenReturn(technologyDto);
    Mockito.when(technologyBlipService.save(any(TechnologyBlipDto.class))).thenReturn(technologyBlipDto);

    wizardService.createRadarEnv(wizard);
    Mockito.verify(radarService, Mockito.times(1)).save(any(Radar.class));
    Mockito.verify(ringService, Mockito.times(4)).save(any(RingDto.class));
    Mockito.verify(segmentService, Mockito.times(4)).save(any(SegmentDto.class));
    Mockito.verify(technologyService, Mockito.times(1)).save(any(TechnologyDto.class));
    Mockito.verify(technologyBlipService, Mockito.times(2)).save(any(TechnologyBlipDto.class));
  }

/*
  @Test
  void shouldCreateRadar() throws Exception {
    final RadarDto radarDto =new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My title");
    radarDto.setDescription("My Description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(wizardService).createRadar

  }

   */
}
