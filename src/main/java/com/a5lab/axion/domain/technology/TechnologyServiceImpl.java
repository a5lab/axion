package com.a5lab.axion.domain.technology;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TechnologyServiceImpl implements TechnologyService {
  private final TechnologyRepository technologyRepository;
  private final TechnologyMapper entryMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<TechnologyDto> findAll() {
    return technologyRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(entryMapper::toDto).collect(Collectors.toList());
  }


  @Override
  @Transactional(readOnly = true)
  public Page<TechnologyDto> findAll(Pageable pageable) {
    return technologyRepository.findAll(pageable).map(entryMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TechnologyDto> findById(Long id) {
    return technologyRepository.findById(id).map(entryMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Technology> findByTitle(String title) {
    return technologyRepository.findByTitle(title);
  }

  @Override
  @Transactional
  public TechnologyDto save(TechnologyDto technologyDto) {
    return entryMapper.toDto(technologyRepository.save(entryMapper.toEntity(technologyDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    technologyRepository.deleteById(id);
  }
}
