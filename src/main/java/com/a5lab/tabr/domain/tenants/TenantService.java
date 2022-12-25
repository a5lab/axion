package com.a5lab.tabr.domain.tenants;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantService {

  Page<TenantRecord> findAll(Pageable pageable);

  void deleteById(long id);
}
