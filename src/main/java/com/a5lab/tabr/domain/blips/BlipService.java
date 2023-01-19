package com.a5lab.tabr.domain.blips;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlipService {

  Collection<Blip> findAll();

  Page<BlipDto> findAll(Pageable pageable);

  Optional<BlipDto> findById(Long id);

  BlipDto save(BlipDto blipDto);

  void deleteById(Long id);
}
