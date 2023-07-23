package com.a5lab.axion.domain.technology_blip;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
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
public class TechnologyBlipServiceImpl implements TechnologyBlipService {

  private final Validator validator;

  private final MessageSource messageSource;

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
    List<ModelError> modelErrorList = new LinkedList<>();

    // Check uniqueness by radar id and technology_id
    List<TechnologyBlip> technologyBlipList =
        this.technologyBlipRepository.findByRadarIdAndTechnologyId(technologyBlipDto.getRadarId(),
            technologyBlipDto.getTechnologyId());
    for (TechnologyBlip technologyBlipItem : technologyBlipList) {
      modelErrorList.addAll(
          new TechnologyBlipUniqueFieldsSaveApprover(messageSource, technologyBlipDto, technologyBlipItem).approve());
    }

    // Throw exception if violations are exists
    TechnologyBlip technologyBlip = technologyBlipMapper.toEntity(technologyBlipDto);
    Set<ConstraintViolation<TechnologyBlip>> constraintViolationSet = validator.validate(technologyBlip);
    if (!modelErrorList.isEmpty() || !constraintViolationSet.isEmpty()) {
      for (ConstraintViolation<TechnologyBlip> constraintViolation : constraintViolationSet) {
        modelErrorList.add(new ModelError(constraintViolation.getMessageTemplate(), constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()));
      }
      String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
      throw new ValidationException(errorMessage, modelErrorList);
    }
    return technologyBlipMapper.toDto(technologyBlipRepository.save(technologyBlip));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    technologyBlipRepository.deleteById(id);
  }
}
