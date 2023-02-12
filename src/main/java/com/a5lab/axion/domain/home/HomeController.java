package com.a5lab.axion.domain.home;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.a5lab.axion.domain.radars.Radar;
import com.a5lab.axion.domain.radars.RadarService;

@Controller
@RequiredArgsConstructor
public class HomeController {

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
