package com.a5lab.tabr.domain.entries;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EntryService {

  Collection<Entry> findAll();

  Page<EntryDto> findAll(Pageable pageable);

  Optional<EntryDto> findById(Long id);

  EntryDto save(EntryDto entryDto);

  void deleteById(Long id);
}
