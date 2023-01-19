package com.a5lab.tabr.domain.entries;

import java.util.Collection;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class EntryServiceImpl implements EntryService {
  private final EntryRepository entryRepository;
  private final EntryMapper entryMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Entry> findAll() {
    return entryRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<EntryDto> findAll(Pageable pageable) {
    return entryRepository.findAll(pageable).map(entryMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<EntryDto> findById(Long id) {
    return entryRepository.findById(id).map(entryMapper::toDto);
  }

  @Override
  @Transactional
  public EntryDto save(EntryDto entryDto) {
    return entryMapper.toDto(entryRepository.save(entryMapper.toEntity(entryDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    entryRepository.deleteById(id);
  }
}
