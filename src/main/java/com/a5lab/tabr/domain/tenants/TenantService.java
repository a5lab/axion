package com.a5lab.tabr.domain.tenants;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

  Collection<Tenant> findAll();

  Page<TenantDTO> findAll(Pageable pageable);

  Optional<TenantDTO> findById(Long id);

  TenantDTO save(TenantDTO entity);

  void deleteById(Long id);
}
