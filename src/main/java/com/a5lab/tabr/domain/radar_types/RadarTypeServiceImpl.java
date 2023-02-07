package com.a5lab.tabr.domain.radar_types;

import java.util.Collection;
import java.util.Optional;

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

  @Override
  @Transactional(readOnly = true)
  public Collection<RadarType> findAll() {
    return radarTypeRepository.findAll(
        Sort.by(Sort.Direction.ASC, "radar.title")
            .and(Sort.by(Sort.Direction.ASC, "segment.title"))
            .and(Sort.by(Sort.Direction.ASC, "ring.title"))
            .and(Sort.by(Sort.Direction.ASC, "entry.title"))
    );
  }


  @Override
  @Transactional(readOnly = true)
  public Page<RadarType> findAll(Pageable pageable) {
    return radarTypeRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RadarType> findById(Long id) {
    return radarTypeRepository.findById(id);
  }

}
