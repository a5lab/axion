package com.a5lab.tabr.domain.radars;

import java.util.Collection;
import java.util.List;
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
public class RadarServiceImpl implements RadarService {
  private final RadarRepository radarRepository;
  private final RadarMapper radarMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<RadarDto> findAll() {
    return radarRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(radarMapper::toDto).collect(Collectors.toList());
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
  @Transactional(readOnly = true)
  public Optional<RadarDto> findByPrimary(boolean primary) {
    List<Radar> radarList = radarRepository.findByPrimary(primary);
    if (radarList.isEmpty()) {
      return Optional.ofNullable(null);
    } else {
      return Optional.of(radarList.get(0)).map(radarMapper::toDto);
    }
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
