package com.a5lab.tabr.domain.profile;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

  @GetMapping("/account/profile")
  public String index() {
    return "account/profile";
  }

}
