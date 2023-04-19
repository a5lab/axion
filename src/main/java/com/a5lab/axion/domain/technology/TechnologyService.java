package com.a5lab.axion.domain.technology;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TechnologyService {

  Collection<TechnologyDto> findAll();

  Page<TechnologyDto> findAll(TechnologyFilter technologyFilter, Pageable pageable);

  Optional<TechnologyDto> findById(Long id);

  Optional<TechnologyDto> findByTitle(String title);

  TechnologyDto save(TechnologyDto technologyDto);

  void deleteById(Long id);
}
