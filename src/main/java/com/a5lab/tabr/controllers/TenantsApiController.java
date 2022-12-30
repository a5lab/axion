package com.a5lab.tabr.controllers;

import jakarta.validation.Valid;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a5lab.tabr.domain.tenants.Tenant;
import com.a5lab.tabr.domain.tenants.TenantService;

@RestController
@RequiredArgsConstructor
public class TenantsApiController {

  private final TenantService tenantService;

  @GetMapping("/api/tenants")
  public List<Tenant> index() {
    return tenantService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
