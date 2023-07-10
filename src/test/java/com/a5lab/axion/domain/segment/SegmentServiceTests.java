package com.a5lab.axion.domain.segment;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;

class SegmentServiceTests extends AbstractServiceTests {
  @MockBean
  private SegmentRepository segmentRepository;

  @MockBean
  private RadarRepository radarRepository;

  @Autowired
  private SegmentMapper segmentMapper;

  @Autowired
  private SegmentService segmentService;

  @Test
  void shouldFindAllSegments() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(1);

    List<Segment> segmentList = List.of(segment);
    Mockito.when(segmentRepository.findAll(any(Sort.class))).thenReturn(segmentList);

    Collection<SegmentDto> segmentDtoCollection = segmentService.findAll();
    Assertions.assertEquals(1, segmentDtoCollection.size());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getId(), segment.getId());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getTitle(), segment.getTitle());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getDescription(), segment.getDescription());
  }

  @Test
  void shouldFindAllSegmentsWithEmptyFilter() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(1);

    List<Segment> segmentList = List.of(segment);
    Page<Segment> page = new PageImpl<>(segmentList);
    Mockito.when(segmentRepository.findAll(ArgumentMatchers.<Specification<Segment>>any(), any(Pageable.class)))
        .thenReturn(page);

    SegmentFilter segmentFilter = new SegmentFilter();
    Pageable pageable = PageRequest.of(0, 10, Sort.by("title,asc"));
    Page<SegmentDto> segmentDtoPage = segmentService.findAll(segmentFilter, pageable);
    Assertions.assertEquals(1, segmentDtoPage.getSize());
    Assertions.assertEquals(0, segmentDtoPage.getNumber());
    Assertions.assertEquals(1, segmentDtoPage.getTotalPages());
    Assertions.assertEquals(segmentDtoPage.iterator().next().getId(), segment.getId());
    Assertions.assertEquals(segmentDtoPage.iterator().next().getTitle(), segment.getTitle());
    Assertions.assertEquals(segmentDtoPage.iterator().next().getDescription(), segment.getDescription());

    // Mockito.verify(segmentRepository).findAll(
    //    Specification.allOf((root, query, criteriaBuilder) -> null), pageable);
  }

  @Test
  void shouldFindByIdSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(segmentRepository.findById(segment.getId())).thenReturn(Optional.of(segment));

    Optional<SegmentDto> segmentDtoOptional = segmentService.findById(segment.getId());
    Assertions.assertTrue(segmentDtoOptional.isPresent());
    Assertions.assertEquals(segment.getId(), segmentDtoOptional.get().getId());
    Assertions.assertEquals(segment.getTitle(), segmentDtoOptional.get().getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDtoOptional.get().getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDtoOptional.get().getPosition());
    Mockito.verify(segmentRepository).findById(segment.getId());
  }

  @Test
  void shouldFindByTitleSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(segmentRepository.findByTitle(segment.getTitle())).thenReturn(Optional.of(segment));

    Optional<SegmentDto> segmentDtoOptional = segmentService.findByTitle(segment.getTitle());
    Assertions.assertTrue(segmentDtoOptional.isPresent());
    Assertions.assertEquals(segment.getId(), segmentDtoOptional.get().getId());
    Assertions.assertEquals(segment.getTitle(), segmentDtoOptional.get().getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDtoOptional.get().getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDtoOptional.get().getPosition());
    Mockito.verify(segmentRepository).findByTitle(segment.getTitle());
  }

  @Test
  void shouldSaveSegment() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar description");
    radar.setPrimary(true);
    radar.setActive(false);

    final Segment segment = new Segment();
    segment.setId(2L);
    segment.setRadar(radar);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(segmentRepository.save(any())).thenReturn(segment);
    Mockito.when(radarRepository.findById(radar.getId())).thenReturn(Optional.of(radar));

    SegmentDto segmentDto = segmentService.save(segmentMapper.toDto(segment));
    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Mockito.verify(segmentRepository).save(any());
    Mockito.verify(radarRepository, times(2)).findById(radar.getId());
  }

  @Test
  void shouldFailToSaveSegmentDueToRadarIsActive() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar description");
    radar.setPrimary(true);
    radar.setActive(true);

    final Segment segment = new Segment();
    segment.setId(2L);
    segment.setRadar(radar);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(radarRepository.findById(radar.getId())).thenReturn(Optional.of(radar));

    ValidationException exception =
        catchThrowableOfType(() -> segmentService.save(segmentMapper.toDto(segment)), ValidationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
    System.out.println(exception.getMessage());

    Mockito.verify(radarRepository, times(2)).findById(radar.getId());
  }

  @Test
  void shouldDeleteSegment() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setTitle("My radar title");
    radar.setPrimary(true);
    radar.setActive(false);

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(segmentRepository.findById(any())).thenReturn(Optional.of(segment));
    Mockito.doAnswer((i) -> null).when(segmentRepository).deleteById(segment.getId());

    segmentService.deleteById(segment.getId());
    Mockito.verify(segmentRepository).findById(segment.getId());
    Mockito.verify(segmentRepository).deleteById(segment.getId());
  }

  @Test
  void shouldFailToDeleteSegmentDueToRadarIsActive() {
    final Radar radar = new Radar();
    radar.setId(1L);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setTitle("My radar title");
    radar.setPrimary(true);
    radar.setActive(true);

    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(radar);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(1);

    Mockito.when(segmentRepository.findById(any())).thenReturn(Optional.of(segment));

    ValidationException exception =
        catchThrowableOfType(() -> segmentService.deleteById(segment.getId()), ValidationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
    Assertions.assertEquals(exception.getMessage(), "Segment can't be deleted for active radar.");
    Assertions.assertTrue(segment.getId().describeConstable().isPresent());

    Mockito.verify(segmentRepository).findById(segment.getId());
  }
}
