package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractMapperTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

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
    radar.setTitle("My title");
    radar.setDescription("My description");
    radar.setPrimary(true);
    radar.setActive(true);

    // Create ring
    final Ring ring = new Ring();
    ring.setId(2L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);
    radar.setRingList(List.of(ring));

    // Create segment
    final Segment segment = new Segment();
    segment.setId(3L);
    segment.setRadar(radar);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);
    radar.setSegmentList(List.of(segment));

    // Create technology
    final var technology = new Technology();
    technology.setId(4L);
    technology.setTitle("My  title");
    technology.setWebsite("https://www.example.com");
    technology.setDescription("My description");
    technology.setMoved(0);
    technology.setActive(true);

    // Create technologyBlip
    final var technologyBlip = new TechnologyBlip();
    technologyBlip.setId(5L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);
    radar.setTechnologyBlipList(List.of(technologyBlip));

    RadarDto radarDto = radarMapper.toDto(radar);
    Assertions.assertEquals(radarDto.getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDto.getDescription(), radar.getDescription());
    Assertions.assertNotNull(radarDto.isPrimary());
    Assertions.assertNotNull(radarDto.isActive());

    // For RingList
    Assertions.assertNotNull(radarDto.getRingList());
    Assertions.assertEquals(1, radarDto.getRingList().size());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getId(), ring.getId());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getTitle(), ring.getTitle());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getDescription(), ring.getDescription());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getPosition(), ring.getPosition());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().getColor(), ring.getColor());
    Assertions.assertEquals(radarDto.getRingList().iterator().next().isActive(), ring.isActive());

    // For SegmentList
    Assertions.assertNotNull(radarDto.getSegmentList());
    Assertions.assertEquals(1, radarDto.getSegmentList().size());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getId(), segment.getId());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getTitle(), segment.getTitle());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getDescription(), segment.getDescription());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().getPosition(), segment.getPosition());
    Assertions.assertEquals(radarDto.getSegmentList().iterator().next().isActive(), segment.isActive());

    // For TechnologyBlipList
    Assertions.assertNotNull(radarDto.getTechnologyBlipList());
    Assertions.assertEquals(1, radarDto.getTechnologyBlipList().size());
    Assertions.assertEquals(radarDto.getTechnologyBlipList().iterator().next().getId(), technologyBlip.getId());
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
  public void testToEntityAllLists() {
    // Create radarType
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setTitle("My title");
    radarType.setDescription("My description");

    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    // Create radar
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setRadarTypeId(radarType.getId());
    radarDto.setTitle("My radar title");
    radarDto.setDescription("My radar description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    // Create ring
    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setRadarTitle(radarDto.getTitle());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setColor("My color");
    ringDto.setPosition(0);
    ringDto.setActive(true);
    radarDto.setRingList(List.of(ringDto));

    // Create technology
    final var technologyDto = new TechnologyDto();
    technologyDto.setId(3L);
    technologyDto.setTitle("My  title");
    technologyDto.setWebsite("https://www.example.com");
    technologyDto.setDescription("My description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    // Create segment
    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(4L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setRadarTitle(radarDto.getTitle());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);
    radarDto.setSegmentList(List.of(segmentDto));

    // Create technologyBlip
    final var technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    radarDto.setTechnologyBlipList(List.of(technologyBlipDto));

    Radar radar = radarMapper.toEntity(radarDto);

    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());
    Assertions.assertNotNull(radar.isPrimary());
    Assertions.assertNotNull(radar.isActive());

    // For RingList
    Assertions.assertNotNull(radar.getRingList());
    Assertions.assertEquals(1, radar.getRingList().size());
    Assertions.assertEquals(radar.getRingList().iterator().next().getId(), ringDto.getId());
    Assertions.assertEquals(radar.getRingList().iterator().next().getTitle(), ringDto.getTitle());
    Assertions.assertEquals(radar.getRingList().iterator().next().getDescription(), ringDto.getDescription());
    Assertions.assertEquals(radar.getRingList().iterator().next().getPosition(), ringDto.getPosition());
    Assertions.assertEquals(radar.getRingList().iterator().next().getColor(), ringDto.getColor());
    Assertions.assertEquals(radar.getRingList().iterator().next().isActive(), ringDto.isActive());

    // For SegmentList
    Assertions.assertNotNull(radar.getSegmentList());
    Assertions.assertEquals(1, radar.getRingList().size());
    Assertions.assertEquals(radar.getSegmentList().iterator().next().getId(), segmentDto.getId());
    Assertions.assertEquals(radar.getSegmentList().iterator().next().getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(radar.getSegmentList().iterator().next().getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(radar.getSegmentList().iterator().next().getPosition(), ringDto.getPosition());
    Assertions.assertEquals(radar.getSegmentList().iterator().next().isActive(), ringDto.isActive());

    // For TechnologyBlipList
    Assertions.assertNotNull(radar.getTechnologyBlipList());
    Assertions.assertEquals(1, radar.getRingList().size());
    Assertions.assertEquals(radar.getTechnologyBlipList().iterator().next().getId(), technologyBlipDto.getId());

    Mockito.verify(radarTypeRepository).findById(radarType.getId());
  }
}