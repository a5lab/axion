package com.a5lab.tabr.domain.tenants;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

  Page<TenantRecord> findAll(Pageable pageable);

  Optional<TenantRecord> findById(Long id);

  void deleteById(Long id);
}
