package com.a5lab.tabr.domain.radars;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RadarsHomeController {

  private final RadarService radarService;

  @GetMapping({"/", "/home"})
  public ModelAndView index() {
    List<Radar> radarList = radarService.findByPrimaryAndActive(true, true);
    ModelAndView modelAndView = new ModelAndView("home/index");
    if (!radarList.isEmpty()) {
      modelAndView.addObject("radar", radarList.get(0));
    }
    return modelAndView;
  }

}
