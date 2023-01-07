package com.a5lab.tabr.domain.tenants;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

  Collection<Tenant> findAll();

  Page<TenantDto> findAll(Pageable pageable);

  Optional<TenantDto> findById(Long id);

  TenantDto create(TenantDto tenantDto);

  TenantDto update(Long id, TenantDto tenantDto);

  void deleteById(Long id);
}
