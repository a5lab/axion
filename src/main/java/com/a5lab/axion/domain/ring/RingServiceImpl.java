package com.a5lab.axion.domain.ring;

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
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;

@RequiredArgsConstructor
@Service
@Transactional
public class RingServiceImpl implements RingService {
  private final Validator validator;
  private final MessageSource messageSource;
  private final RingRepository ringRepository;
  private final RadarRepository radarRepository;
  private final RingMapper ringMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<RingDto> findAll() {
    return ringRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(ringMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<RingDto> findAll(RingFilter ringFilter, Pageable pageable) {
    return ringRepository.findAll((root, query, builder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (ringFilter != null && ringFilter.getTitle() != null
          && !ringFilter.getTitle().isBlank()) {
        predicateList.add(builder.like(root.get("title"), ringFilter.getTitle()));
      }
      return builder.and(predicateList.toArray(new Predicate[] {}));
    }, pageable).map(ringMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RingDto> findById(Long id) {
    return ringRepository.findById(id).map(ringMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<RingDto> findByTitle(String title) {
    return ringRepository.findByTitle(title).map(ringMapper::toDto);
  }

  @Override
  @Transactional
  public RingDto save(RingDto ringDto) {
    List<ModelError> modelErrorList = new LinkedList<>();
    Optional<Radar> radarOptional = radarRepository.findById(ringDto.getRadarId());
    Optional<Ring> ringOptional = Optional.empty();
    if (ringDto.getId() != null) {
      ringOptional = ringRepository.findById(ringDto.getId());
    }
    if (radarOptional.isPresent() && ringOptional.isPresent()) {
      modelErrorList.addAll(
          new RadarActiveSaveApprover(messageSource, radarOptional.get(), ringOptional.get()).approve());
    }

    Ring ring = ringMapper.toEntity(ringDto);
    // Throw exception if violations are exists
    Set<ConstraintViolation<Ring>> constraintViolationSet = validator.validate(ring);
    if (!modelErrorList.isEmpty() || !constraintViolationSet.isEmpty()) {
      for (ConstraintViolation<Ring> constraintViolation : constraintViolationSet) {
        modelErrorList.add(new ModelError(constraintViolation.getMessageTemplate(), constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()));
      }
      String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
      throw new ValidationException(errorMessage, modelErrorList);
    }
    return ringMapper.toDto(ringRepository.save(ring));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    Optional<Ring> ringOptional = ringRepository.findById(id);
    if (ringOptional.isPresent()) {
      List<ModelError> modelErrorList = new LinkedList<>();
      modelErrorList.addAll(new RadarActiveDeleteApprover(messageSource, ringOptional.get()).approve());
      if (!modelErrorList.isEmpty()) {
        String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
        throw new ValidationException(errorMessage, modelErrorList);
      }
      ringRepository.deleteById(id);
    }
  }
}
