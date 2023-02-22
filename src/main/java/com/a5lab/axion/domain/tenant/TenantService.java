package com.a5lab.axion.domain.tenant;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

  Collection<TenantDto> findAll();

  Page<TenantDto> findAll(TenantFilter tenantFilter, Pageable pageable);

  Optional<TenantDto> findById(Long id);

  TenantDto save(TenantDto tenantDto);

  void deleteById(Long id);
}
