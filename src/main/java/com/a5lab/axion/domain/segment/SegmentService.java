package com.a5lab.axion.domain.segment;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SegmentService {

  Collection<SegmentDto> findAll();

  Page<SegmentDto> findAll(SegmentFilter segmentFilter, Pageable pageable);

  Optional<SegmentDto> findById(Long id);

  Optional<SegmentDto> findByTitle(String title);

  SegmentDto save(SegmentDto segmentDto);

  void deleteById(Long id);
}
