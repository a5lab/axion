package com.a5lab.tabr.domain.rings;

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
public class RingServiceImpl implements RingService {
  private final RingRepository ringRepository;
  private final RingMapper ringMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<RingDto> findAll() {
    return ringRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(ringMapper::toDto).collect(Collectors.toList());
  }


  @Override
  @Transactional(readOnly = true)
  public Page<RingDto> findAll(Pageable pageable) {
    return ringRepository.findAll(pageable).map(ringMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RingDto> findById(Long id) {
    return ringRepository.findById(id).map(ringMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Ring> findByTitle(String title) {
    return ringRepository.findByTitle(title);
  }

  @Override
  @Transactional
  public RingDto save(RingDto ringDto) {
    return ringMapper.toDto(ringRepository.save(ringMapper.toEntity(ringDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    ringRepository.deleteById(id);
  }
}
