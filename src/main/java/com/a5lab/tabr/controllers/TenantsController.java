package com.a5lab.tabr.controllers;

// import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import com.a5lab.tabr.domain.tenants.Tenant;
// import com.a5lab.tabr.domain.tenants.TenantRepository;

@Controller
public class TenantsController {

  // private final TenantRepository tenantRepository;

  @GetMapping("/settings/tenants")
  public ModelAndView index() {

    // Temp solution
    java.util.List<Tenant> tenants = new java.util.ArrayList<>();
    Tenant tenant1 = new Tenant();
    tenant1.setTitle("My org1");
    tenant1.setDescription("My dec11");
    tenants.add(tenant1);

    Tenant tenant2 = new Tenant();
    tenant2.setTitle("My org1");
    tenant2.setDescription("My dec11");
    tenants.add(tenant2);

    ModelAndView modelAndView = new ModelAndView("/settings/tenants");
    // modelAndView.addObject(tenantRepository.findAll());
    modelAndView.addObject(tenants);
    return modelAndView;
  }


}
