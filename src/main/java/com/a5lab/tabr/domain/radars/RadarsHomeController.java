package com.a5lab.tabr.domain.radars;

import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.domain.radar_types.RadarTypeService;
import com.a5lab.tabr.utils.FlashMessages;

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
