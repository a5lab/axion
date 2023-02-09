package com.a5lab.tabr.domain.rings;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RingService {

  Collection<RingDto> findAll();

  Page<RingDto> findAll(Pageable pageable);

  Optional<RingDto> findById(Long id);

  Optional<Ring> findByTitle(String title);

  RingDto save(RingDto ringDto);

  void deleteById(Long id);
}
