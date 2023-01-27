package com.a5lab.tabr.domain.tenants;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/tenants")
@RequiredArgsConstructor
public class TenantsApiController {

  private final TenantService tenantService;

  @GetMapping("")
  public Collection<TenantDto> index() {
    return tenantService.findAll();
  }
}
