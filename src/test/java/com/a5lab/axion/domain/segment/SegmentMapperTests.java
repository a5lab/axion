package com.a5lab.axion.domain.segment;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;

class SegmentMapperTests extends AbstractMapperTests {

  @MockBean
  private RadarRepository radarRepository;

  @MockBean
  private TechnologyBlipMapper technologyBlipMapper;

  @Autowired
  private SegmentMapper segmentMapper;

  @Test
  public void testToDtoWithNull() {
    final var segmentDto = segmentMapper.toDto(null);

    Assertions.assertNull(segmentDto);
  }

  @Test
  public void testToDtoAllFields() {
    final var segment = new Segment();
    segment.setId(1L);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final var segmentDto = segmentMapper.toDto(segment);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());
  }

  @Test
  public void testToDtoAllLists() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setTitle("My radarType title");
    radarType.setDescription("My radarType description");

    // Create radar
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setRadarType(radarType);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);
    radar.setRingList(List.of(new Ring()));
    radar.setTechnologyBlipList(List.of(new TechnologyBlip()));
    radar.setSegmentList(List.of(new Segment()));

    // Create ring
    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setRadarId(radar.getId());
    ringDto.setRadarTitle(radar.getTitle());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setColor("My ring color");
    ringDto.setPosition(1);
    ringDto.setActive(true);
    ringDto.setTechnologyBlipDtoList(List.of(new TechnologyBlipDto()));

    // Create segment
    final Segment segment = new Segment();
    segment.setId(3L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(2);
    segment.setActive(true);
    segment.setTechnologyBlipList(List.of(new TechnologyBlip()));

    // Create technology
    final var technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology title");
    technologyDto.setWebsite("https://www.example.com");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    // Create technologyBlip
    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radar.getId());
    technologyBlipDto.setSegmentId(segment.getId());
    technologyBlipDto.setSegmentTitle(segment.getTitle());
    technologyBlipDto.setSegmentPosition(segment.getPosition());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setTechnologyWebsite(technologyDto.getWebsite());
    technologyBlipDto.setTechnologyMoved(technologyDto.getMoved());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setRingPosition(ringDto.getPosition());

    Mockito.when(technologyBlipMapper.toDto(any())).thenReturn(technologyBlipDto);

    final var segmentDto = segmentMapper.toDto(segment);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Assertions.assertEquals(segment.getRadar().getId(), segmentDto.getRadarId());
    Assertions.assertEquals(segment.getRadar().getTitle(), segmentDto.getRadarTitle());

    Assertions.assertNotNull(segmentDto.getTechnologyBlipDtoList());
    Assertions.assertEquals(1, segmentDto.getTechnologyBlipDtoList().size());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getId(), technologyBlipDto.getId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getRadarTitle(),
        technologyBlipDto.getRadarTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getTechnologyId(),
        technologyBlipDto.getTechnologyId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getTechnologyTitle(),
        technologyBlipDto.getTechnologyTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getTechnologyWebsite(),
        technologyBlipDto.getTechnologyWebsite());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getTechnologyMoved(),
        technologyBlipDto.getTechnologyMoved());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().isTechnologyActive(),
        technologyBlipDto.isTechnologyActive());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getSegmentId(),
        technologyBlipDto.getSegmentId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getSegmentTitle(),
        technologyBlipDto.getSegmentTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getSegmentPosition(),
        technologyBlipDto.getSegmentPosition());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getRingId(),
        technologyBlipDto.getRingId());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getRingTitle(),
        technologyBlipDto.getRingTitle());
    Assertions.assertEquals(segmentDto.getTechnologyBlipDtoList().iterator().next().getRingPosition(),
        technologyBlipDto.getRingPosition());

    Mockito.verify(technologyBlipMapper).toEntity(any());
  }

  @Test
  public void testToEntityWithNull() {
    final var segment = segmentMapper.toEntity(null);

    Assertions.assertNull(segment);
  }

  @Test
  public void testToEntityAllFields() {
    final Radar radar = new Radar();
    radar.setId(0L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final var segmentDto = new SegmentDto();
    segmentDto.setId(1L);
    segmentDto.setRadarId(radar.getId());
    segmentDto.setRadarTitle(radar.getTitle());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final var segment = segmentMapper.toEntity(segmentDto);

    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Mockito.verify(radarRepository).findById(segment.getRadar().getId());
  }

  @Test
  public void testToEntityAllLists() {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setTitle("My radarType title");
    radarType.setDescription("My radarType description");

    final Radar radar = new Radar();
    radar.setId(2L);
    radar.setRadarType(radarType);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(true);
    radar.setSegmentList(List.of(new Segment()));
    radar.setTechnologyBlipList(List.of(new TechnologyBlip()));

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));

    final Segment segment = new Segment();
    segment.setId(3L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final var segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setRadarId(radar.getId());
    segmentDto.setRadarTitle(radar.getTitle());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);
    segmentDto.setTechnologyBlipDtoList(List.of(new TechnologyBlipDto()));

    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(4L);
    technologyBlip.setRadar(radar);
    technologyBlip.setSegment(segment);

    Mockito.when(technologyBlipMapper.toEntity(any())).thenReturn(technologyBlip);

    final var MappedSegment = segmentMapper.toEntity(segmentDto);

    Assertions.assertEquals(MappedSegment.getId(), segmentDto.getId());
    Assertions.assertEquals(MappedSegment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(MappedSegment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(MappedSegment.getPosition(), segmentDto.getPosition());

    Assertions.assertEquals(MappedSegment.getRadar().getId(), segmentDto.getRadarId());
    Assertions.assertEquals(MappedSegment.getRadar().getTitle(), segmentDto.getRadarTitle());
    Assertions.assertEquals(MappedSegment.getId(),
        MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getId());
    Assertions.assertEquals(MappedSegment.getTitle(),
        MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getTitle());
    Assertions.assertEquals(MappedSegment.getDescription(),
        MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getDescription());
    Assertions.assertEquals(MappedSegment.getPosition(),
        MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getPosition());
    Assertions.assertEquals(MappedSegment.getRadar().getId(),
        MappedSegment.getTechnologyBlipList().iterator().next().getRadar().getId());

    Assertions.assertNotNull(MappedSegment.getTechnologyBlipList());
    Assertions.assertEquals(1, MappedSegment.getTechnologyBlipList().size());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getRadar().getId(),
        technologyBlip.getRadar().getId());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getRadar().getTitle(),
        technologyBlip.getRadar().getTitle());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getId(),
        technologyBlip.getSegment().getId());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getTitle(),
        technologyBlip.getSegment().getTitle());
    Assertions.assertEquals(MappedSegment.getTechnologyBlipList().iterator().next().getSegment().getPosition(),
        technologyBlip.getSegment().getPosition());

    Mockito.verify(radarRepository).findById(MappedSegment.getRadar().getId());
    Mockito.verify(technologyBlipMapper).toEntity(any());
  }
}
