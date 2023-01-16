package com.a5lab.tabr.domain.radars;

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
public class RadarServiceImpl implements RadarService {
  private final RadarRepository radarRepository;
  private final RadarMapper radarMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<Radar> findAll() {
    return radarRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<RadarDto> findAll(Pageable pageable) {
    return radarRepository.findAll(pageable).map(radarMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RadarDto> findById(Long id) {
    return radarRepository.findById(id).map(radarMapper::toDto);
  }

  @Override
  @Transactional
  public RadarDto save(RadarDto radarDto) {
    return radarMapper.toDto(radarRepository.save(radarMapper.toEntity(radarDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    radarRepository.deleteById(id);
  }
}