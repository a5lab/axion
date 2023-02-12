package com.a5lab.axion.domain.notification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotificationController {

  @GetMapping("/account/notifications")
  public String index() {
    return "account/notifications";
  }

}
