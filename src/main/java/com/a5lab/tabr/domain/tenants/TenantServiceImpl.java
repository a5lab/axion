package com.a5lab.tabr.domain.tenants;

import java.util.Collection;
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
  public Collection<Tenant> findAll() {
    return tenantRepository.findAll();
  }


  @Override
  @Transactional(readOnly = true)
  public Page<TenantDto> findAll(Pageable pageable) {
    return tenantRepository.findAll(pageable).map(tenantMapper::toDto);
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
