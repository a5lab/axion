package com.a5lab.tabr.domain;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/legal")
public class LegalController {

  @GetMapping("/privacy")
  public ModelAndView privacy() {
    ModelAndView modelAndView = new ModelAndView("/legal/privacy");
    return modelAndView;
  }

  @GetMapping("/terms")
  public ModelAndView terms() {
    ModelAndView modelAndView = new ModelAndView("/legal/terms");
    return modelAndView;
  }
}
