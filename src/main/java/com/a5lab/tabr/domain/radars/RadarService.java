package com.a5lab.tabr.domain.radars;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarService {

  Collection<Radar> findAll();

  Page<Radar> findAll(Pageable pageable);

  Optional<Radar> findById(Long id);

  Optional<Radar> findByPrimary(boolean primary);

  Radar save(Radar radarDto);

  void deleteById(Long id);
}
