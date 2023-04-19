package com.a5lab.axion.domain.radar;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarService {

  Collection<RadarDto> findAll();

  Page<RadarDto> findAll(RadarFilter radarFilter, Pageable pageable);

  Optional<RadarDto> findById(Long id);

  Optional<Radar> findByPrimary(boolean primary);

  List<RadarDto> findByPrimaryAndActive(boolean primary, boolean active);

  RadarDto save(RadarDto radarDto);

  void deleteById(Long id);
}
