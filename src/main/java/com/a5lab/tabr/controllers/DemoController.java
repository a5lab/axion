package com.a5lab.tabr.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@Controller
public class DemoController {

  @GetMapping("/demo")
  public ModelAndView demo(HttpServletRequest request) {
    Person artem = new Person("Artem", 20);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("person", artem);

    var acceptValue = request.getHeader(HttpHeaders.ACCEPT);
    if (acceptValue != null && acceptValue.toLowerCase().contains("text/html")) {
      modelAndView.setViewName("demo");
    } else {
      var view = new MappingJackson2JsonView();
      view.setExtractValueFromSingleKeyModel(true);
      modelAndView.setView(view);
    }

    return modelAndView;
  }

  public record Person(String name, Integer age) {
  }
}
