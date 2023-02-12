package com.a5lab.axion.domain.radar;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarService {

  Collection<Radar> findAll();

  Page<Radar> findAll(Pageable pageable);


  Optional<Radar> findById(Long id);

  Optional<Radar> findByPrimary(boolean primary);

  List<Radar> findByPrimaryAndActive(boolean primary, boolean active);

  Radar save(Radar radar);

  void deleteById(Long id);
}
