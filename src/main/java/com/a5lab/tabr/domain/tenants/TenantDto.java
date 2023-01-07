package com.a5lab.tabr.domain.tenants;

public record TenantDto(Long id, String title, String description) {
  public TenantDto() {
    this(null, "", "");
  }
}
