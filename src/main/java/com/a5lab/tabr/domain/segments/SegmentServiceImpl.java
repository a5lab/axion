package com.a5lab.tabr.domain.segments;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class SegmentServiceImpl implements SegmentService {
  private final SegmentRepository segmentRepository;
  private final SegmentMapper segmentMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Segment> findAll() {
    return segmentRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<SegmentDto> findAll(Pageable pageable) {
    return segmentRepository.findAll(pageable).map(segmentMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SegmentDto> findById(Long id) {
    return segmentRepository.findById(id).map(segmentMapper::toDto);
  }

  @Override
  @Transactional
  public SegmentDto save(SegmentDto segmentDto) {
    return segmentMapper.toDto(segmentRepository.save(segmentMapper.toEntity(segmentDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    segmentRepository.deleteById(id);
  }
}
