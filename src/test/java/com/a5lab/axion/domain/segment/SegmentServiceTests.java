package com.a5lab.axion.domain.segment;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractServiceTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

class SegmentServiceTests extends AbstractServiceTests {
  private final SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);

  private final SegmentMapper segmentMapper = Mappers.getMapper(SegmentMapper.class);

  private final SegmentService segmentService = new SegmentServiceImpl(segmentRepository, segmentMapper);

  @Test
  void shouldFindAllSegments() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setRadar(null);
    segment.setTitle("My segment");
    segment.setDescription("My segment description");
    segment.setPosition(0);
    segment.setActive(true);
    List<Segment> segmentList = List.of(segment);
    Mockito.when(segmentRepository.findAll(any(Sort.class))).thenReturn(segmentList);

    Collection<SegmentDto> segmentDtoCollection = segmentService.findAll();
    Assertions.assertEquals(1, segmentDtoCollection.size());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getId(), segment.getId());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getTitle(), segment.getTitle());
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getDescription(), segment.getDescription());
  }


  @Test
  void shouldFindByIdSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.when(segmentRepository.findById(segment.getId())).thenReturn(Optional.of(segment));
    Optional<SegmentDto> segmentDtoOptional = segmentService.findById(segment.getId());
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
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.when(segmentRepository.findByTitle(segment.getTitle())).thenReturn(Optional.of(segment));
    Optional<Segment> segmentOptional = segmentService.findByTitle(segment.getTitle());
    Assertions.assertEquals(segment.getId(), segmentOptional.get().getId());
    Assertions.assertEquals(segment.getTitle(), segmentOptional.get().getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentOptional.get().getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentOptional.get().getPosition());
    Mockito.verify(segmentRepository).findByTitle(segment.getTitle());
  }

  @Test
  void shouldSaveSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.when(segmentRepository.save(any())).thenReturn(segment);

    SegmentDto segmentDto = segmentService.save(segmentMapper.toDto(segment));
    Assertions.assertEquals(segment.getId(), segmentDto.getId());
    Assertions.assertEquals(segment.getTitle(), segmentDto.getTitle());
    Assertions.assertEquals(segment.getDescription(), segmentDto.getDescription());
    Assertions.assertEquals(segment.getPosition(), segmentDto.getPosition());

    Mockito.verify(segmentRepository).save(any());
  }

  @Test
  void shouldDeleteSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("My title");
    segment.setDescription("My description");
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.doAnswer((i) -> null).when(segmentRepository).deleteById(segment.getId());

    segmentService.deleteById(segment.getId());
    Mockito.verify(segmentRepository).deleteById(segment.getId());
  }
}
