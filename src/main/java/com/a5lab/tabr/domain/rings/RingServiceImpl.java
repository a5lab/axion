package com.a5lab.tabr.domain.rings;

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
public class RingServiceImpl implements RingService {
  private final RingRepository ringRepository;
  private final RingMapper ringMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Ring> findAll() {
    return ringRepository.findAll();
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
