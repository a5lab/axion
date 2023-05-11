package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class RadarMapperTests  extends AbstractMapperTests {
  @MockBean
  private RadarTypeRepository radarTypeRepository;

  @Autowired
  private RadarMapper radarMapper;

  @Autowired
  private RingMapper ringMapper;

  @Autowired
  private SegmentMapper segmentMapper;

  @Autowired
  private TechnologyBlipMapper technologyBlipMapper;

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
    List<Ring> ringList = new ArrayList<>();
    List<Segment> segmentList = new ArrayList<>();
    List<TechnologyBlip> technologyBlipList = new ArrayList<>();

    Assertions.assertEquals(radarDto.getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDto.getDescription(), radar.getDescription());
    Assertions.assertNotNull(ringList);
    Assertions.assertNotNull(segmentList);
    Assertions.assertNotNull(technologyBlipList);
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
    radarDto.setTitle("My title1");
    radarDto.setDescription("My description1");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    final Radar radar = radarMapper.toEntity(radarDto);

    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());
  }

  @Test
  public void testAllListToListDto() {
    // Create radar
    final Radar radar = new Radar();
    RadarDto radarDto = radarMapper.toDto(radar);

    // Create ring
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);
    RingDto ringDto = ringMapper.toDto(ring);
    List<RingDto> ringDtoList = List.of(ringDto);

    // Create segment
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);
    SegmentDto segmentDto = segmentMapper.toDto(segment);
    List<SegmentDto> segmentDtoList = List.of(segmentDto);

    // Create technologyBlip
    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(10L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
//    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);
    TechnologyBlipDto technologyBlipDto = technologyBlipMapper.toDto(technologyBlip);
    List<TechnologyBlipDto> technologyBlipDtoList = List.of(technologyBlipDto);

    radarDto.setRingList(ringDtoList);
    radarDto.setSegmentList(segmentDtoList);
    radarDto.setTechnologyBlipList(technologyBlipDtoList);

    Assertions.assertNotNull(radarDto.getRingList());
    Assertions.assertNotNull(radarDto.getSegmentList());
    Assertions.assertNotNull(radarDto.getTechnologyBlipList());
    Assertions.assertNotNull(ringDtoList);
    Assertions.assertNotNull(segmentDtoList);
    Assertions.assertNotNull(technologyBlipDtoList);
  }

}