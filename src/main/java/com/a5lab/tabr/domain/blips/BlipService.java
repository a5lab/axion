package com.a5lab.tabr.domain.blips;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BlipService {

  Collection<BlipDto> findAll();

  Page<BlipDto> findAll(Pageable pageable);

  Optional<BlipDto> findById(Long id);

  BlipDto save(BlipDto blipDto); // TODO: remove

  Blip save(Blip blip);

  void deleteById(Long id);
}
