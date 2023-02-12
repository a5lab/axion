package com.a5lab.axion.domain.blips;

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
public class BlipServiceImpl implements BlipService {
  private final BlipRepository blipRepository;
  private final BlipMapper blipMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<BlipDto> findAll() {
    return blipRepository.findAll(
            Sort.by(Sort.Direction.ASC, "radar.title")
                .and(Sort.by(Sort.Direction.ASC, "segment.title"))
                .and(Sort.by(Sort.Direction.ASC, "ring.title"))
                .and(Sort.by(Sort.Direction.ASC, "entry.title"))
        )
        .stream().map(blipMapper::toDto).collect(Collectors.toList());
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
  public Blip save(Blip blip) {
    return blipRepository.save(blip);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    blipRepository.deleteById(id);
  }
}
