package com.a5lab.tabr.controllers;

import jakarta.validation.Valid;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.a5lab.tabr.domain.tenants.Tenant;
import com.a5lab.tabr.domain.tenants.TenantRecord;
import com.a5lab.tabr.domain.tenants.TenantService;

@RestController
@RequiredArgsConstructor
public class TenantsApiController {

  private final TenantService tenantService;

  @GetMapping("/api/tenants")
  public List<Tenant> index() {
    return tenantService.findAll();
  }

  @PostMapping("/api/tenants/create")
  public String create(@Valid TenantRecord tenant, BindingResult result) {
    if (result.hasErrors()) {
      return "/settings/tenants/add";
    }
    tenantService.saveAndFlush(tenant);
    return "redirect:/settings/tenants/";
  }

  @PutMapping("/api/tenants/update")
  public String update(@Valid TenantRecord tenant, BindingResult result) {
    if (result.hasErrors()) {
      return "/settings/tenants/add";
    }
    tenantService.saveAndFlush(tenant);
    return "redirect:/settings/tenants/";
  }

  @DeleteMapping(value = "/api/tenants/{id}")
  public String delete(@PathVariable("id") long id) {
    tenantService.deleteById(id);
    return "redirect:/settings/tenants";
  }
}
