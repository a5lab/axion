package com.a5lab.axion.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/notifications")
@RequiredArgsConstructor
public class NotificationController {

  @GetMapping("")
  public String index() {
    return "accounts/notification/index";
  }

}
