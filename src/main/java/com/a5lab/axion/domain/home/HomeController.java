package com.a5lab.axion.domain.home;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarFilter;
import com.a5lab.axion.domain.radar.RadarService;

@Controller
@RequiredArgsConstructor
public class HomeController {

  private final RadarService radarService;

  @GetMapping({"/", "/home"})
  public ModelAndView index(@Valid RadarFilter radarFilter, BindingResult bindingResult,
                            Pageable pageable) {

    radarFilter.setFilterByPrimary(true);
    radarFilter.setPrimary(true);
    radarFilter.setFilterByActive(true);
    radarFilter.setActive(true);

    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);

    ModelAndView modelAndView = new ModelAndView("home/index");
    if (!radarDtoPage.isEmpty()) {
      modelAndView.addObject("radarDto", radarDtoPage.iterator().next());
    }
    return modelAndView;
  }

}
