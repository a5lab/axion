package com.a5lab.axion.domain.radar;

import java.util.ArrayList;
import java.util.List;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class RadarMapperTests  extends AbstractMapperTests {

  private final RadarMapper radarMapper = Mappers.getMapper(RadarMapper.class);

  @Test
  void testToDtoWithNull() {
    final RadarDto radarDto = radarMapper.toDto(null);
//    List<Ring> ringList = ringList.getRingList();
//    List<Segment> segmentList = mapper.toDto(null).getSegmentList();
//    List<TechnologyBlip> technologyBlipList = mapper.toDto(null).getTechnologyBlipList();

    Assertions.assertNull(radarDto);
//    Assertions.assertNull(ringList);
//    Assertions.assertNull(segmentList);
//    Assertions.assertNull(technologyBlipList);

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
}