package com.a5lab.tabr.domain.tenants;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Integer> {
  Optional<Tenant> findTenantByTitle(String title);

}
