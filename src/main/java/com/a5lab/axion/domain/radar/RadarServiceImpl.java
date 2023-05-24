package com.a5lab.axion.domain.radar;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
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
  public Page<RadarDto> findAll(RadarFilter radarFilter, Pageable pageable) {
    return radarRepository.findAll((root, query, builder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (radarFilter != null && radarFilter.getTitle() != null
          && !radarFilter.getTitle().isBlank()) {
        predicateList.add(builder.like(root.get("title"), radarFilter.getTitle()));
      }
      if (radarFilter != null && radarFilter.isFilterByPrimary()) {
        if (radarFilter.isPrimary()) {
          predicateList.add(builder.isTrue(root.get("primary")));
        }
      } else {
        predicateList.add(builder.isFalse(root.get("primary")));
      }
      if (radarFilter != null && radarFilter.isFilterByActive()) {
        if (radarFilter.isActive()) {
          predicateList.add(builder.isTrue(root.get("active")));
        }
      } else {
        predicateList.add(builder.isFalse(root.get("active")));
      }
      return builder.and(predicateList.toArray(new Predicate[] {}));
    }, pageable).map(radarMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RadarDto> findById(Long id) {
    return radarRepository.findById(id).map(radarMapper::toDto);
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
  public List<RadarDto> findByPrimaryAndActive(boolean primary, boolean active) {
    return radarRepository.findByPrimaryAndActive(primary, active)
        .stream().map(radarMapper::toDto).collect(Collectors.toList());
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
