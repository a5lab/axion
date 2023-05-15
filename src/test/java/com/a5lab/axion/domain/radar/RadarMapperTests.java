package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RadarMapperTests extends AbstractMapperTests {

  @MockBean
  private RadarTypeRepository radarTypeRepository;

  @MockBean
  private RingMapper ringMapper;

  @MockBean
  private SegmentMapper segmentMapper;

  @MockBean
  private TechnologyBlipMapper technologyBlipMapper;

  @Autowired
  private RadarMapper radarMapper;

  @Test
  void testToDtoWithNull() {
    final RadarDto radarDto = radarMapper.toDto(null);

    Assertions.assertNull(radarDto);
  }

  @Test
  void testToDtoAllFields() {
    final Radar radar = new Radar();
    radar.setId(0L);
    radar.setTitle("My title");
    radar.setDescription("My description");
    radar.setPrimary(true);
    radar.setActive(true);

    final var radarDto = radarMapper.toDto(radar);
    Assertions.assertEquals(radarDto.getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDto.getDescription(), radar.getDescription());
    Assertions.assertNotNull(radarDto.isPrimary());
    Assertions.assertNotNull(radarDto.isActive());
  }

  @Test
  public void testToDtoAllLists() {
    // Create radar
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);
    radar.setRingList(List.of(new Ring()));
    radar.setSegmentList(List.of(new Segment()));
    radar.setTechnologyBlipList(List.of(new TechnologyBlip()));

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setColor("My color");
    ringDto.setPosition(1);
    ringDto.setActive(true);

    Mockito.when(ringMapper.toDto(any())).thenReturn(ringDto);

    final var segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(2);
    segmentDto.setActive(true);

    Mockito.when(segmentMapper.toDto(any())).thenReturn(segmentDto);

    // Create technology
    final var technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology title");
    technologyDto.setWebsite("https://www.example.com");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setRingPosition(ringDto.getPosition());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setTechnologyWebsite(technologyDto.getWebsite());
    technologyBlipDto.setTechnologyMoved(technologyDto.getMoved());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    technologyBlipDto.setSegmentTitle(segmentDto.getTitle());
    technologyBlipDto.setSegmentPosition(segmentDto.getPosition());

    Mockito.when(technologyBlipMapper.toDto(any())).thenReturn(technologyBlipDto);

    // Map object
    RadarDto radarDto = radarMapper.toDto(radar);
    Assertions.assertEquals(radarDto.getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDto.getDescription(), radar.getDescription());
    Assertions.assertNotNull(radarDto.isPrimary());
    Assertions.assertNotNull(radarDto.isActive());

    // Compare ring values
    Assertions.assertNotNull(radarDto.getRingList());
    Assertions.assertEquals(1, radarDto.getRingList().size());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getId(), ringDto.getId());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getTitle(), ringDto.getTitle());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getDescription(), ringDto.getDescription());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getPosition(), ringDto.getPosition());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getColor(), ringDto.getColor());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().isActive(), ringDto.isActive());

    // Compare segment values
    Assertions.assertNotNull(radarDto.getSegmentList());
    Assertions.assertEquals(1, radarDto.getSegmentList().size());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getId(), segmentDto.getId());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getPosition(), segmentDto.getPosition());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().isActive(), segmentDto.isActive());

    // Compare technology blips values
    Assertions.assertNotNull(radarDto.getTechnologyBlipList());
    Assertions.assertEquals(1, radarDto.getTechnologyBlipList().size());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getId(), technologyBlipDto.getId());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getRadarId(),
        technologyBlipDto.getRadarId());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getRadarTitle(),
        technologyBlipDto.getRadarTitle());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getTechnologyId(),
        technologyBlipDto.getTechnologyId());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getTechnologyTitle(),
        technologyBlipDto.getTechnologyTitle());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getTechnologyWebsite(),
        technologyBlipDto.getTechnologyWebsite());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getTechnologyMoved(),
        technologyBlipDto.getTechnologyMoved());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().isTechnologyActive(),
        technologyBlipDto.isTechnologyActive());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getSegmentId(),
        technologyBlipDto.getSegmentId());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getSegmentTitle(),
        technologyBlipDto.getSegmentTitle());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getSegmentPosition(),
        technologyBlipDto.getSegmentPosition());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getRingId(),
        technologyBlipDto.getRingId());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getRingTitle(),
        technologyBlipDto.getRingTitle());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getRingPosition(),
        technologyBlipDto.getRingPosition());

    Mockito.verify(ringMapper).toDto(any());
    Mockito.verify(segmentMapper).toDto(any());
    Mockito.verify(technologyBlipMapper).toDto(any());
  }

  @Test
  void testToEntityWithNull() {
    final Radar radar = radarMapper.toEntity(null);

    Assertions.assertNull(radar);
  }

  @Test
  void testToEntityAllFields() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setTitle("My title");
    radarType.setDescription("My description");

    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(0L);
    radarDto.setRadarTypeId(radarType.getId());
    radarDto.setTitle("My title1");
    radarDto.setDescription("My description1");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    // Map object
    final Radar radar = radarMapper.toEntity(radarDto);
    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());

    Mockito.verify(radarTypeRepository).findById(radarType.getId());
  }

  @Test
  public void testToEntityAllLists() {
    // Create radarType
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setTitle("My radar type title");
    radarType.setDescription("My radar type description");

    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    // Create radar
    final Radar radar = new Radar();
    radar.setId(12L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    // Create radar dto
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(12L);
    radarDto.setRadarTypeId(radarType.getId());
    radarDto.setTitle("My radar title");
    radarDto.setDescription("My radar description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);
    radarDto.setRingList(List.of(new RingDto()));
    radarDto.setSegmentList(List.of(new SegmentDto()));
    radarDto.setTechnologyBlipList(List.of(new TechnologyBlipDto()));

    // Create ring
    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringMapper.toEntity(any())).thenReturn(ring);

    // Create segment
    final Segment segment = new Segment();
    segment.setId(4L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.when(segmentMapper.toEntity(any())).thenReturn(segment);

    // Create technology
    final var technology = new Technology();
    technology.setId(4L);
    technology.setTitle("My technology title");
    technology.setWebsite("https://www.example.com");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    // Create technologyBlip
    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(5L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setSegment(segment);
    technologyBlip.setTechnology(technology);

    Mockito.when(technologyBlipMapper.toEntity(any())).thenReturn(technologyBlip);

    // Map object
    Radar mapped_radar = radarMapper.toEntity(radarDto);
    Assertions.assertEquals(mapped_radar.getId(), radarDto.getId());
    Assertions.assertEquals(mapped_radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(mapped_radar.getDescription(), radarDto.getDescription());
    Assertions.assertNotNull(mapped_radar.isPrimary());
    Assertions.assertNotNull(mapped_radar.isActive());
    Assertions.assertEquals(mapped_radar.getId(), mapped_radar.getRingList().iterator().next().getRadar().getId());
    Assertions.assertEquals(mapped_radar.getId(), mapped_radar.getSegmentList().iterator().next().getRadar().getId());
    Assertions.assertEquals(mapped_radar.getId(),
        mapped_radar.getTechnologyBlipList().iterator().next().getRadar().getId());

    // Compare ring values
    Assertions.assertNotNull(radarDto.getRingList());
    Assertions.assertEquals(1, mapped_radar.getRingList().size());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getId(), ring.getId());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getRadar().getId(), radar.getId());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getTitle(), ring.getTitle());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getDescription(), ring.getDescription());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getPosition(), ring.getPosition());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().getColor(), ring.getColor());
    Assertions.assertEquals(mapped_radar.getRingList().iterator().next().isActive(), ring.isActive());

    // Compare segment values
    Assertions.assertNotNull(mapped_radar.getSegmentList());
    Assertions.assertEquals(1, mapped_radar.getRingList().size());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().getId(), segment.getId());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().getRadar().getId(), radar.getId());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().getTitle(), segment.getTitle());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().getDescription(), segment.getDescription());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().getPosition(), segment.getPosition());
    Assertions.assertEquals(mapped_radar.getSegmentList().iterator().next().isActive(), segment.isActive());

    // Compare technology blips values
    Assertions.assertNotNull(mapped_radar.getTechnologyBlipList());
    Assertions.assertEquals(1, mapped_radar.getRingList().size());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRadar(),
        technologyBlip.getRadar());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRadar().getId(),
        technologyBlip.getRadar().getId());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getTechnology(),
        technologyBlip.getTechnology());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getTechnology().getId(),
        technologyBlip.getTechnology().getId());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getTechnology().getTitle(),
        technologyBlip.getTechnology().getTitle());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getTechnology().getWebsite(),
        technologyBlip.getTechnology().getWebsite());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getTechnology().getMoved(),
        technologyBlip.getTechnology().getMoved());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getSegment().getId(),
        technologyBlip.getSegment().getId());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getSegment().getTitle(),
        technologyBlip.getSegment().getTitle());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getSegment().getPosition(),
        technologyBlip.getSegment().getPosition());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRing(), technologyBlip.getRing());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRing().getId(),
        technologyBlip.getRing().getId());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRing().getTitle(),
        technologyBlip.getRing().getTitle());
    Assertions.assertEquals(mapped_radar.getTechnologyBlipList().iterator().next().getRing().getPosition(),
        technologyBlip.getRing().getPosition());

    Mockito.verify(radarTypeRepository).findById(radarType.getId());
    Mockito.verify(ringMapper).toEntity(any());
    Mockito.verify(segmentMapper).toEntity(any());
    Mockito.verify(technologyBlipMapper).toEntity(any());
  }
}