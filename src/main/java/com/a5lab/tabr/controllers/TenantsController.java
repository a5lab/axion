package com.a5lab.tabr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.a5lab.tabr.domain.tenants.TenantRepository;

@Controller
public class TenantsController {

  private final TenantRepository tenantRepository;

  @GetMapping("/settings/tenants")
  public ModelAndView index() {

    ModelAndView modelAndView = new ModelAndView("/settings/tenants");
    modelAndView.addObject(tenantRepository.findAll());
    return modelAndView;
  }


}
