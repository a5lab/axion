package com.a5lab.axion.domain.technology_blip;

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
public class TechnologyBlipServiceImpl implements TechnologyBlipService {
  private final TechnologyBlipRepository technologyBlipRepository;
  private final TechnologyBlipMapper technologyBlipMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<TechnologyBlipDto> findAll() {
    return technologyBlipRepository.findAll(
            Sort.by(Sort.Direction.ASC, "radars.title")
                .and(Sort.by(Sort.Direction.ASC, "segments.title"))
                .and(Sort.by(Sort.Direction.ASC, "rings.title"))
                .and(Sort.by(Sort.Direction.ASC, "technologies.title"))
        )
        .stream().map(technologyBlipMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TechnologyBlipDto> findAll(TechnologyBlipFilter technologyBlipFilter,
                                         Pageable pageable) {
    return technologyBlipRepository.findAll(pageable).map(technologyBlipMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TechnologyBlipDto> findById(Long id) {
    return technologyBlipRepository.findById(id).map(technologyBlipMapper::toDto);
  }

  @Override
  @Transactional
  public TechnologyBlipDto save(TechnologyBlipDto technologyBlipDto) {
    return technologyBlipMapper.toDto(
        technologyBlipRepository.save(technologyBlipMapper.toEntity(technologyBlipDto)));
  }

  /* fuck
  @Override
  @Transactional
  public TechnologyBlip save(TechnologyBlip technologyBlip) {
    return technologyBlipRepository.save(technologyBlip);
  }
  */

  @Override
  @Transactional
  public void deleteById(Long id) {
    technologyBlipRepository.deleteById(id);
  }
}
