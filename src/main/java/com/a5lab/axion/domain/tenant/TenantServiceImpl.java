package com.a5lab.axion.domain.tenant;

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
public class TenantServiceImpl implements TenantService {

  private final Validator validator;
  private final TenantRepository tenantRepository;
  private final TenantMapper tenantMapper;

  @Override
  @Transactional(readOnly = true)
  public Collection<TenantDto> findAll() {
    return tenantRepository.findAll(Sort.by(Sort.Direction.ASC, "title"))
        .stream().map(tenantMapper::toDto).collect(Collectors.toList());
  }

  @Override
  @Transactional(readOnly = true)
  public Page<TenantDto> findAll(TenantFilter tenantFilter, Pageable pageable) {
    return tenantRepository.findAll((root, query, builder) -> {
      List<Predicate> predicateList = new ArrayList<>();
      if (tenantFilter != null && tenantFilter.getTitle() != null
          && !tenantFilter.getTitle().isBlank()) {
        predicateList.add(builder.like(root.get("title"), tenantFilter.getTitle()));
      }
      return builder.and(predicateList.toArray(new Predicate[] {}));
    }, pageable).map(tenantMapper::toDto);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TenantDto> findById(Long id) {
    return tenantRepository.findById(id).map(tenantMapper::toDto);
  }

  @Override
  @Transactional
  public TenantDto save(TenantDto tenantDto) {
    Tenant tenant = tenantMapper.toEntity(tenantDto);
    // Throw exception if violations are exists
    List<ModelError> modelErrorList = new LinkedList<>();
    Set<ConstraintViolation<Tenant>> constraintViolationSet = validator.validate(tenant);
    if (!constraintViolationSet.isEmpty()) {
      for (ConstraintViolation<Tenant> constraintViolation : constraintViolationSet) {
        modelErrorList.add(new ModelError(constraintViolation.getMessageTemplate(), constraintViolation.getMessage(),
            constraintViolation.getPropertyPath().toString()));
      }
      String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
      throw new ValidationException(errorMessage, modelErrorList);
    }
    return tenantMapper.toDto(tenantRepository.save(tenant));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    tenantRepository.deleteById(id);
  }
}
