package com.a5lab.tabr.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import com.a5lab.tabr.domain.tenants.TenantService;
import com.a5lab.tabr.domain.tenants.TenantRecord;

@Controller
@RequiredArgsConstructor
public class TenantsController {

  private final TenantService tenantService;

  @GetMapping("/settings/tenants")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("tenants",
        tenantService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/settings/tenants/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/add");
    modelAndView.addObject("tenant", new TenantRecord(0L, "", ""));
    return modelAndView;
  }

  @RequestMapping(value = "/settings/tenants/edit/{id}", method = RequestMethod.GET)
  public ModelAndView edit(@PathVariable("id") long id) {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/edit");
    modelAndView.addObject("tenant", tenantService.findById(id));
    return modelAndView;
  }

  @RequestMapping(value = "/settings/tenants/{id}", method = RequestMethod.DELETE)
  public String delete(@PathVariable("id") long id) {
    tenantService.deleteById(id);
    return "redirect:/settings/tenants";
  }
}
