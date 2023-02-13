package com.a5lab.axion.domain.technology_blip;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechnologyBlipService {

  Collection<TechnologyBlipDto> findAll();

  Page<TechnologyBlipDto> findAll(Pageable pageable);

  Optional<TechnologyBlipDto> findById(Long id);

  TechnologyBlipDto save(TechnologyBlipDto technologyBlipDto); // TODO: remove

  TechnologyBlip save(TechnologyBlip technologyBlip);

  void deleteById(Long id);
}
