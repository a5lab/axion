package com.a5lab.tabr.domain.entities;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntityService {

  Collection<Entity> findAll();

  Page<EntityDto> findAll(Pageable pageable);

  Optional<EntityDto> findById(Long id);

  EntityDto save(EntityDto entityDto);

  void deleteById(Long id);
}
