package com.a5lab.tabr.domain.tenants;

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
  public Page<TenantRecord> findAll(Pageable pageable) {
    return tenantRepository.findAll(pageable).map(tenantMapper::toRecord);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<TenantRecord> findById(Long id) {
    return tenantRepository.findById(id).map(tenantMapper::toRecord);
  }

  @Override
  @Transactional
  public TenantRecord save(TenantRecord entity) {
    return tenantMapper.toRecord(tenantRepository.saveAndFlush(tenantMapper.toEntity(entity)));
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    tenantRepository.deleteById(id);
  }
}
