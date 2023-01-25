package com.a5lab.tabr.domain.radars;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarService {

  Collection<Radar> findAll();

  Page<RadarDto> findAll(Pageable pageable);

  Optional<RadarDto> findById(Long id);

  Optional<RadarDto> findByPrimary(boolean primary);

  RadarDto save(RadarDto radarDto);

  void deleteById(Long id);
}
