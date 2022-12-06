package com.a5lab.tabr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TenantsController {

  @GetMapping("/settings/tenants")
  public String index() {
    return "/settings/tenants";
  }

}
