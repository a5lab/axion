package com.a5lab.axion.domain.segment;

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
import com.a5lab.axion.domain.ring.RadarActiveSaveApprover;

@RequiredArgsConstructor
@Service
@Transactional
public class SegmentServiceImpl implements SegmentService {
  private final Validator validator;
  private final MessageSource messageSource;
  private final SegmentRepository segmentRepository;
  private final RadarRepository radarRepository;
  private final SegmentMapper segmentMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<SegmentDto> findAll() {
    return segmentRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(segmentMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<SegmentDto> findAll(SegmentFilter segmentFilter, Pageable pageable) {
    return segmentRepository.findAll((root, query, builder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (segmentFilter != null && segmentFilter.getTitle() != null
          && !segmentFilter.getTitle().isBlank()) {
        predicateList.add(builder.like(root.get("title"), segmentFilter.getTitle()));
      }
      return builder.and(predicateList.toArray(new Predicate[] {}));
    }, pageable).map(segmentMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SegmentDto> findById(Long id) {
    return segmentRepository.findById(id).map(segmentMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<SegmentDto> findByTitle(String title) {
    return segmentRepository.findByTitle(title).map(segmentMapper::toDto);
  }


  @Override
  @Transactional
  public SegmentDto save(SegmentDto segmentDto) {
    List<ModelError> modelErrorList = new LinkedList<>();
    Optional<Radar> radarOptional = radarRepository.findById(segmentDto.getRadarId());
    if (radarOptional.isPresent()) {
      modelErrorList.addAll(new RadarActiveSaveApprover(messageSource, radarOptional.get()).approve());
    }

    Segment segment = segmentMapper.toEntity(segmentDto);
    // Throw exception if violations are exists
    Set<ConstraintViolation<Segment>> constraintViolationSet = validator.validate(segment);
    if (!modelErrorList.isEmpty() || !constraintViolationSet.isEmpty()) {
      for (ConstraintViolation<Segment> constraintViolation : constraintViolationSet) {
        modelErrorList.add(new ModelError(constraintViolation.getMessageTemplate(), constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()));
      }
      String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
      throw new ValidationException(errorMessage, modelErrorList);
    }
    return segmentMapper.toDto(segmentRepository.save(segment));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    Optional<Segment> segmentOptional = segmentRepository.findById(id);
    if (segmentOptional.isPresent()) {
      List<ModelError> modelErrorList = new LinkedList<>();
      modelErrorList.addAll(new RadarActiveDeleteApprover(messageSource, segmentOptional.get()).approve());
      if (!modelErrorList.isEmpty()) {
        String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
        throw new ValidationException(errorMessage, modelErrorList);
      }
      segmentRepository.deleteById(id);
    }
  }
}
