package com.a5lab.tabr.domain.blips;

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
public class BlipServiceImpl implements BlipService {
  private final BlipRepository blipRepository;
  private final BlipMapper blipMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Blip> findAll() {
    return blipRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<BlipDto> findAll(Pageable pageable) {
    return blipRepository.findAll(pageable).map(blipMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<BlipDto> findById(Long id) {
    return blipRepository.findById(id).map(blipMapper::toDto);
  }

  @Override
  @Transactional
  public BlipDto save(BlipDto blipDto) {
    return blipMapper.toDto(blipRepository.save(blipMapper.toEntity(blipDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    blipRepository.deleteById(id);
  }
}
