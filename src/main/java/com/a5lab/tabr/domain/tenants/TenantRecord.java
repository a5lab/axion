package com.a5lab.tabr.domain.tenants;

import jakarta.validation.constraints.NotBlank;

public record TenantRecord(Long id, String title, String description) {
}
