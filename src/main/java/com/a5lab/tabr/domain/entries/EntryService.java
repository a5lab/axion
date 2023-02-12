package com.a5lab.tabr.domain.entries;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryService {

  Collection<EntryDto> findAll();

  Page<EntryDto> findAll(Pageable pageable);

  Optional<EntryDto> findById(Long id);

  Optional<Entry> findByTitle(String title);

  EntryDto save(EntryDto entryDto);

  void deleteById(Long id);
}
