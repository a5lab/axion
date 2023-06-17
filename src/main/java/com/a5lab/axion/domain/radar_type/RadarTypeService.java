package com.a5lab.axion.domain.radar_type;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RadarTypeService {

  Collection<RadarTypeDto> findAll();

  Page<RadarTypeDto> findAll(Pageable pageable);

  Optional<RadarTypeDto> findById(Long id);

  Optional<RadarTypeDto> findByCode(String code);
}
