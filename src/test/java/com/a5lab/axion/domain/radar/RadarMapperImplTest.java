package com.a5lab.axion.domain.radar;


import java.util.List;

import com.a5lab.axion.domain.AbstractMapperTests;
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
import org.springframework.beans.factory.annotation.Autowired;

public class RadarMapperImplTest  extends AbstractMapperTests {

  @Autowired
  private RadarMapper radarMapper;

  @Autowired
  private RingMapper ringMapper;

  @Autowired
  private SegmentMapper segmentMapper;

  @Autowired
  private TechnologyBlipMapper technologyBlipMapper;

  @Test
  public void testAllListToListDto() {
    //create radar
    final Radar radar = new Radar();
    RadarDto radarDto = radarMapper.toDto(radar);

    //create ring
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);
    RingDto ringDto = ringMapper.toDto(ring);
    List<RingDto> ringDtoList = List.of(ringDto);

    //create segment
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);
    SegmentDto segmentDto = segmentMapper.toDto(segment);
    List<SegmentDto> segmentDtoList = List.of(segmentDto);

    //create technologyBlip
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

