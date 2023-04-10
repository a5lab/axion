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
  private SegmentRepository segmentRepository = Mockito.mock(SegmentRepository.class);

  private SegmentMapper segmentMapper = Mappers.getMapper(SegmentMapper.class);

  private SegmentService segmentService = new SegmentServiceImpl(segmentRepository, segmentMapper);

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
    Assertions.assertEquals(segmentDtoCollection.iterator().next().getDescription(),
        segment.getDescription());

  }
  /*
  @Test
  void shouldSaveSegments() {
    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setTitle("my title");
    segmentDto.setDescription("my description");
    segmentDto.setColor("my color");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);
    //    not sure for this
    Mockito.when(segmentMapper.toDto(segmentRepository.save(segmentMapper.toEntity(segmentDto)))).thenReturn(Optional.of(segmentDto));
    segmentService.save(segmentDto);
    Mockito.verify(segmentRepository).save(segmentMapper.toEntity(segmentDto));
  }
   */

  @Test
  void shouldFindByIdSegments() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("my title");
    segment.setDescription("my description");
    segment.setPosition(0);
    segment.setActive(true);

    List<Segment> segmentList = List.of(segment);
    Mockito.when(segmentRepository.findById(segment.getId())).thenReturn(Optional.of(segment));

    segmentService.findById(segment.getId());
    Mockito.verify(segmentRepository).findById(segment.getId());
  }

  @Test
  void shouldFindByTitleSegments() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("my title");
    segment.setDescription("my description");
    segment.setPosition(0);
    segment.setActive(true);

    Mockito.when(segmentRepository.findByTitle(segment.getTitle())).thenReturn(Optional.of(segment));
    segmentService.findByTitle(segment.getTitle());

    Mockito.verify(segmentRepository).findByTitle(segment.getTitle());

  }

  @Test
  void shouldDeleteSegment() {
    final Segment segment = new Segment();
    segment.setId(10L);
    segment.setTitle("my title");
    segment.setDescription("my description");
    segment.setPosition(0);
    segment.setActive(true);

    List<Segment> segmentList = List.of(segment);
    Mockito.when(segmentRepository.findById(segment.getId())).thenReturn(Optional.of(segment));

    segmentService.deleteById(segment.getId());
    Mockito.verify(segmentRepository).deleteById(segment.getId());

  }
}
