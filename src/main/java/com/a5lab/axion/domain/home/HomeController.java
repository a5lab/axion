package com.a5lab.axion.domain.home;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final RadarService radarService;

  @GetMapping({"/", "/home"})
  public ModelAndView index() {
    List<RadarDto> radarDtoList = radarService.findByPrimaryAndActive(true, true);
    ModelAndView modelAndView = new ModelAndView("home/index");
    if (!radarDtoList.isEmpty()) {
      modelAndView.addObject("radarDto", radarDtoList.get(0));
    }
    return modelAndView;
  }

}
