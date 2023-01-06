package com.a5lab.tabr.domain.tenants;

public record TenantDTO(Long id, String title, String description) {
  public TenantDTO() {
    this(null, "", "");
  }
}
