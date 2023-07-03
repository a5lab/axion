package com.a5lab.axion.domain.radar;

import jakarta.persistence.criteria.Predicate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;


@RequiredArgsConstructor
@Service
@Transactional
public class RadarServiceImpl implements RadarService {

  private final Validator validator;

  private final MessageSource messageSource;

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
        } else {
          predicateList.add(builder.isFalse(root.get("primary")));
        }
      }
      if (radarFilter != null && radarFilter.isFilterByActive()) {
        if (radarFilter.isActive()) {
          predicateList.add(builder.isTrue(root.get("active")));
        } else {
          predicateList.add(builder.isFalse(root.get("active")));
        }
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
  @Transactional
  public RadarDto save(RadarDto radarDto) {
    Radar radar = radarMapper.toEntity(radarDto);
    List<ModelError> modelErrorList = new LinkedList<>();
    if (radarDto.isPrimary()) {
      // Find another primary radar
      List<Radar> radarList = radarRepository.findByPrimary(true);
      for (Radar radarItem : radarList) {
        modelErrorList.addAll(new RadarPrimaryApprover(messageSource, radarDto, radarItem).approve());
      }
    }

    // Throw exception if violations are exists
    Set<ConstraintViolation<Radar>> constraintViolationSet = validator.validate(radar);
    if (!modelErrorList.isEmpty() || !constraintViolationSet.isEmpty()) {
      for (ConstraintViolation<Radar> constraintViolation : constraintViolationSet) {
        modelErrorList.add(new ModelError(constraintViolation.getMessageTemplate(), constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()));
      }
      throw new ValidationException(modelErrorList);
    }
    return radarMapper.toDto(radarRepository.save(radar));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    radarRepository.deleteById(id);
  }
}
