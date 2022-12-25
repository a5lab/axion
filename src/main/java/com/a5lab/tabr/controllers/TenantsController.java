package com.a5lab.tabr.controllers;

// import java.util.List;

import com.a5lab.tabr.domain.tenants.TenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
// import com.a5lab.tabr.domain.tenants.TenantRepository;

@Controller
@RequiredArgsConstructor
public class TenantsController {

  private final TenantService tenantService;

  @GetMapping("/settings/tenants")
  public ModelAndView index() {

    ModelAndView modelAndView = new ModelAndView("/settings/tenants");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    modelAndView.addObject("tenants",
        tenantService.findAll(Pageable.ofSize(100)).getContent());

    return modelAndView;
  }


}
