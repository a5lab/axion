package com.a5lab.tabr.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  @GetMapping({"/", "home.html"})
  public ModelAndView main() {
    return new ModelAndView("home.html");
  }

}
