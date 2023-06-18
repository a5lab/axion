package com.a5lab.axion.domain.radar_type;

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
public class RadarTypeServiceImpl implements RadarTypeService {
  private final RadarTypeRepository radarTypeRepository;
  private final RadarTypeMapper radarTypeMapper;


  @Override
  @Transactional(readOnly = true)
  public Collection<RadarTypeDto> findAll() {
    return radarTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(radarTypeMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RadarTypeDto> findAll(Pageable pageable) {
    return radarTypeRepository.findAll(pageable).map(radarTypeMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RadarTypeDto> findById(Long id) {
    return radarTypeRepository.findById(id).map(radarTypeMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RadarTypeDto> findByCode(String code) {
    return radarTypeRepository.findByCode(code).map(radarTypeMapper::toDto);
  }
}
