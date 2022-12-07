package com.a5lab.tabr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationsController {

  @GetMapping("/account/notifications")
  public String index() {
    return "account/notifications";
  }

}
