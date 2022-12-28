package com.a5lab.tabr.controllers;

import com.a5lab.tabr.domain.radars.Radar;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  @GetMapping({"/", "home.html"})
  public ModelAndView index() {
    Radar radar = new Radar();
    radar.setTitle("myTitle");
    radar.setDescription("myDescriptions");

    ModelAndView modelAndView = new ModelAndView("home");
    modelAndView.addObject(radar);
    return modelAndView;
  }
}
