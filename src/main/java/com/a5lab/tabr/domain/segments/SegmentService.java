package com.a5lab.tabr.domain.segments;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SegmentService {

  Collection<SegmentDto> findAll();

  Page<SegmentDto> findAll(Pageable pageable);

  Optional<SegmentDto> findById(Long id);

  Optional<Segment> findByTitle(String title);

  SegmentDto save(SegmentDto segmentDto);

  void deleteById(Long id);
}
