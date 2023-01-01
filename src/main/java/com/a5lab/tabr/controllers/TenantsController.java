package com.a5lab.tabr.controllers;

import jakarta.validation.Valid;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.domain.tenants.TenantRecord;
import com.a5lab.tabr.domain.tenants.TenantService;

@Controller
@RequestMapping("/settings/tenants")
@RequiredArgsConstructor
public class TenantsController {

  private final TenantService tenantService;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("tenants",
        tenantService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<TenantRecord> tenantRecord = tenantService.findById(id);
    if (tenantRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("/settings/tenants/show");
      modelAndView.addObject("tenant", tenantRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute("msg_error", "Invalid tenant id. ");
      return new ModelAndView("redirect:/settings/tenants");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("/settings/tenants/add");
    modelAndView.addObject("tenant", new TenantRecord(0L, "this title", "this description"));
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public String create(@Valid TenantRecord tenantRecord, BindingResult result,
                       RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) { // Doesn't work
      return "/settings/tenants/add";
    }
    tenantService.saveAndFlush(tenantRecord);
    redirectAttributes.addFlashAttribute("msg_info", "The tenant has been created successfully. ");
    return "redirect:/settings/tenants";
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<TenantRecord> tenantRecord = tenantService.findById(id);
    if (tenantRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("/settings/tenants/edit");
      modelAndView.addObject("tenant", tenantRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute("msg_error", "Invalid tenant id. ");
      return new ModelAndView("redirect:/settings/tenants");
    }
  }

  @PostMapping("/update/{id}")
  public String update(@PathVariable("id") Long id, @Valid TenantRecord tenantRecord,
                       BindingResult result, RedirectAttributes redirectAttributes) {
    if (result.hasErrors()) {
      return "/settings/tenants/edit/{id}";
    }
    tenantService.saveAndFlush(tenantRecord); // !!! a new insert? constraint failure
    redirectAttributes.addFlashAttribute("msg_info", "The tenant has been updated successfully. ");
    return "redirect:/settings/tenants";
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    tenantService.deleteById(id);
    redirectAttributes.addFlashAttribute("msg_info", "The tenant has been deleted successfully. ");
    return "redirect:/settings/tenants";
  }
}
