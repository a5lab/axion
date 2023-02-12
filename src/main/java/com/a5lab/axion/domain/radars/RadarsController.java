package com.a5lab.axion.domain.radars;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/radars")
@RequiredArgsConstructor
public class RadarsController {

  private final RadarService radarService;

  @GetMapping("")
  public ModelAndView index() {
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    ModelAndView modelAndView = new ModelAndView("radars/index");
    modelAndView.addObject("radars",
        radarService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

}
