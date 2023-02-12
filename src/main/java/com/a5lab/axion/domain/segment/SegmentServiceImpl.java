package com.a5lab.axion.domain.segment;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public Collection<SegmentDto> findAll() {
    return segmentRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(segmentMapper::toDto).collect(Collectors.toList());
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
  @Transactional(readOnly = true)
  public Optional<Segment> findByTitle(String title) {
    return segmentRepository.findByTitle(title);
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
