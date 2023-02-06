package com.a5lab.tabr.domain.radars;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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

  @Override
  @Transactional(readOnly = true)
  public Collection<Radar> findAll() {
    return radarRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
  }


  @Override
  @Transactional(readOnly = true)
  public Page<Radar> findAll(Pageable pageable) {
    return radarRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Radar> findById(Long id) {
    return radarRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Radar> findByPrimary(boolean primary) {
    List<Radar> radarList = radarRepository.findByPrimary(primary);
    if (radarList.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(radarList.get(0));
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<Radar> findByPrimaryAndActive(boolean primary, boolean active){
    return radarRepository.findByPrimaryAndActive(primary, active);
  }


  @Override
  @Transactional
  public Radar save(Radar radar) {
    return radarRepository.save(radar);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    radarRepository.deleteById(id);
  }
}
