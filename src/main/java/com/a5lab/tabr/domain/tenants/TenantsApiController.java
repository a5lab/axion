package com.a5lab.tabr.domain.tenants;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class TenantsApiController {

  private final TenantService tenantService;

  @GetMapping("/tenants")
  public Collection<Tenant> index() {
    return tenantService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
