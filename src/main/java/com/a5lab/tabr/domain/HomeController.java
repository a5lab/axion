package com.a5lab.tabr.domain;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.a5lab.tabr.domain.radars.RadarDto;
import com.a5lab.tabr.domain.radars.RadarService;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final RadarService radarService;

  @GetMapping({"/", "home"})
  public ModelAndView index() {

    Optional<RadarDto> radarDtoOptional = radarService.findByPrimary(true);
    ModelAndView modelAndView = new ModelAndView("home");
    if (radarDtoOptional.isPresent()) {
      modelAndView.addObject("radarDto", radarDtoOptional.get());
    }
    return modelAndView;

    /* todo remove it
    Radar radar = new Radar();
    radar.setTitle("myTitle");
    radar.setDescription("myDescriptions");

    ModelAndView modelAndView = new ModelAndView("home");
    modelAndView.addObject(radar);
    return modelAndView;
     */
  }
}
