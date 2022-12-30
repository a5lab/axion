package com.a5lab.tabr.controllers;

import jakarta.validation.Valid;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.a5lab.tabr.domain.tenants.TenantRecord;
import com.a5lab.tabr.domain.tenants.TenantService;

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

  @GetMapping("/settings/tenants/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id) {
    TenantRecord tenantRecord = new TenantRecord(0L, "this title", "this description");
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/show");
    modelAndView.addObject("tenant", tenantRecord);
    return modelAndView;
  }

  @GetMapping("/settings/tenants/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/add");
    modelAndView.addObject("tenant", new TenantRecord(0L, "this title", "this description"));
    return modelAndView;
  }

  @PostMapping(value = "/settings/tenants/create")
  public String create(@Valid TenantRecord tenantRecord, BindingResult result) {
    if (result.hasErrors()) {
      return "/settings/tenants/add";
    }
    tenantService.saveAndFlush(tenantRecord);
    return "redirect:/settings/tenants";
  }

  @GetMapping(value = "/settings/tenants/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id) {
    Optional<TenantRecord> tenantRecord = tenantService.findById(id);
    if(tenantRecord.isPresent()){
      ModelAndView modelAndView = new ModelAndView("/settings/tenants/edit");
      modelAndView.addObject("tenant", tenantRecord.get());
      return modelAndView;
    } else {
      return new ModelAndView("redirect:/settings/tenants/");
    }
  }

  @PostMapping("/settings/tenants/update/{id}")
  public String update(@PathVariable("id") Long id, @Valid TenantRecord tenantRecord, BindingResult result) {
    if (result.hasErrors()) {
      return "/settings/tenants/edit/{id}";
    }
    tenantService.saveAndFlush(tenantRecord); // !!! a new insert? constraint failure
    return "redirect:/settings/tenants/";
  }

  @GetMapping(value = "/settings/tenants/{id}")
  public String delete(@PathVariable("id") Long id) {
    tenantService.deleteById(id);
    return "redirect:/settings/tenants";
  }
}
