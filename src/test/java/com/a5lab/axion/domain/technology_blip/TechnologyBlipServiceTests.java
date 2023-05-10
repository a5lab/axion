package com.a5lab.axion.domain.technology_blip;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;

import com.a5lab.axion.domain.technology.TechnologyRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

class TechnologyBlipServiceTests extends AbstractServiceTests {
  @MockBean
  private RingRepository ringRepository;
  @MockBean
  private RadarRepository radarRepository;
  @MockBean
  private SegmentRepository segmentRepository;
  @MockBean
  private TechnologyRepository technologyRepository;
  @MockBean
  private TechnologyBlipRepository technologyBlipRepository;
  @Autowired
  private TechnologyBlipMapper technologyBlipMapper;
  @Autowired
  private TechnologyBlipService technologyBlipService;
  /*
  @Test
  void shouldFindAllTechnologyBlips() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(null);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("#fbdb84");
    ring.setActive(true);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(10L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);
    List<TechnologyBlip> technologyBlipList = List.of(technologyBlip);

    Mockito.when(technologyBlipRepository.findAll(any(Sort.class))).thenReturn(technologyBlipList);

    Collection<TechnologyBlipDto> technologyBlipDtoCollection = technologyBlipService.findAll();
    Assertions.assertEquals(1, technologyBlipDtoCollection.size());
    Assertions.assertEquals(technologyBlipDtoCollection.iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(technologyBlipDtoCollection.iterator().next().getRadarId(),
        technologyBlip.getRadar().getId());
    Assertions.assertEquals(technologyBlipDtoCollection.iterator().next().getSegmentId(),
        technologyBlip.getSegment().getId());
    Assertions.assertEquals(technologyBlipDtoCollection.iterator().next().getRingId(),
        technologyBlip.getRing().getId());
    Assertions.assertEquals(technologyBlipDtoCollection.iterator().next().getTechnologyId(),
        technologyBlip.getTechnology().getId());

    Mockito.verify(technologyBlipRepository).findAll(any(Sort.class));
  }

  @Test
  void shouldFindAllTechnologyBlipWithFilter() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(11L);
    segment.setRadar(radar);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(12L);
    ring.setRadar(radar);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("#fbdb84");
    ring.setActive(true);

    final Technology technology = new Technology();
    technology.setId(13L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(14L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);

    List<TechnologyBlip> technologyBlipList = List.of(technologyBlip);
    Page<TechnologyBlip> page = new PageImpl<>(technologyBlipList);
    Mockito.when(technologyBlipRepository.findAll(any(Pageable.class))).thenReturn(page);

    TechnologyBlipFilter technologyBlipFilter = new TechnologyBlipFilter();
    Pageable pageable = PageRequest.of(0, 10, Sort.by("technology_blips.id,asc"));
    Page<TechnologyBlipDto> technologyBlipDtoPage = technologyBlipService.findAll(technologyBlipFilter, pageable);
    Assertions.assertEquals(1, technologyBlipDtoPage.getSize());
    Assertions.assertEquals(0, technologyBlipDtoPage.getNumber());
    Assertions.assertEquals(1, technologyBlipDtoPage.getSize());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getId(), technologyBlip.getId());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRadarId(),
        technologyBlip.getRadar().getId());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getSegmentId(),
        technologyBlip.getSegment().getId());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getRingId(),
        technologyBlip.getRing().getId());
    Assertions.assertEquals(technologyBlipDtoPage.iterator().next().getTechnologyId(),
        technologyBlip.getTechnology().getId());

    Mockito.verify(technologyBlipRepository).findAll(any(Pageable.class));
  }

  @Test
  void shouldFindByIdTechnologyBlips() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(null);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("#fbdb84");
    ring.setActive(true);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(10L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);

    Mockito.when(technologyBlipRepository.findById(any())).thenReturn(Optional.of(technologyBlip));

    Optional<TechnologyBlipDto> technologyBlipDtoOptional = technologyBlipService.findById(technologyBlip.getId());
    Assertions.assertEquals(technologyBlip.getId(), technologyBlipDtoOptional.get().getId());
    Assertions.assertEquals(technologyBlip.getRadar().getId(), technologyBlipDtoOptional.get().getRadarId());
    Assertions.assertEquals(technologyBlip.getRing().getId(), technologyBlipDtoOptional.get().getRingId());
    Assertions.assertEquals(technologyBlip.getSegment().getId(), technologyBlipDtoOptional.get().getSegmentId());
    Assertions.assertEquals(technologyBlip.getTechnology().getId(), technologyBlipDtoOptional.get().getTechnologyId());

    Mockito.verify(technologyBlipRepository).findById(technologyBlip.getId());
  }

  @Test
  void shouldSaveTechnologyBlipDto() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(null);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("#fbdb84");
    ring.setActive(true);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(10L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);

    Mockito.when(radarRepository.findById(any())).thenReturn(Optional.of(radar));
    Mockito.when(segmentRepository.findById(any())).thenReturn(Optional.of(segment));
    Mockito.when(ringRepository.findById(any())).thenReturn(Optional.of(ring));
    Mockito.when(technologyRepository.findById(any())).thenReturn(Optional.of(technology));
    Mockito.when(technologyBlipRepository.save(any())).thenReturn(technologyBlip);

    TechnologyBlipDto technologyBlipDto = technologyBlipService.save(technologyBlipMapper.toDto(technologyBlip));

    Assertions.assertEquals(technologyBlip.getId(), technologyBlipDto.getId());
    Assertions.assertEquals(technologyBlip.getRadar().getId(), technologyBlipDto.getRadarId());
    Assertions.assertEquals(technologyBlip.getSegment().getId(), technologyBlipDto.getSegmentId());
    Assertions.assertEquals(technologyBlip.getRing().getId(), technologyBlipDto.getRingId());
    Assertions.assertEquals(technologyBlip.getTechnology().getId(), technologyBlipDto.getTechnologyId());

    Mockito.verify(technologyBlipRepository).save(any());
    Mockito.verify(radarRepository).findById(radar.getId());
    Mockito.verify(ringRepository).findById(ring.getId());
    Mockito.verify(segmentRepository).findById(segment.getId());
    Mockito.verify(technologyRepository).findById(technology.getId());
  }

  @Test
  void shouldDeleteTechnologyBlip() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment title");
    segment.setDescription("My segment description");
    segment.setPosition(1);
    segment.setActive(true);

    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setRadar(null);
    ring.setTitle("My ring title");
    ring.setDescription("My ring description");
    ring.setPosition(0);
    ring.setColor("#fbdb84");
    ring.setActive(true);

    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(1);
    technology.setActive(true);

    final TechnologyBlip technologyBlip = new TechnologyBlip();
    technologyBlip.setId(10L);
    technologyBlip.setRadar(radar);
    technologyBlip.setRing(ring);
    technologyBlip.setTechnology(technology);
    technologyBlip.setSegment(segment);

    Mockito.doAnswer((i) -> null).when(technologyBlipRepository).deleteById(technologyBlip.getId());

    technologyBlipService.deleteById(technologyBlip.getId());

    Mockito.verify(technologyBlipRepository).deleteById(technologyBlip.getId());
  }
   */
}
