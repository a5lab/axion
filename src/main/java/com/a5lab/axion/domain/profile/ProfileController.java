package com.a5lab.axion.domain.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account/profile")
@RequiredArgsConstructor
public class ProfileController {

  @GetMapping("")
  public String index() {
    return "accounts/profile/index";
  }

}
