package com.a5lab.axion.domain.tenant;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class TenantServiceImpl implements TenantService {

  private final TenantRepository tenantRepository;
  private final TenantMapper tenantMapper;

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
    return tenantMapper.toDto(tenantRepository.save(tenantMapper.toEntity(tenantDto)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    tenantRepository.deleteById(id);
  }
}
