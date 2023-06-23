package com.a5lab.axion.domain.radar;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarService {

  Collection<RadarDto> findAll();

  Page<RadarDto> findAll(RadarFilter radarFilter, Pageable pageable);

  Optional<RadarDto> findById(Long id);

  RadarDto save(RadarDto radarDto);

  void deleteById(Long id);
}
