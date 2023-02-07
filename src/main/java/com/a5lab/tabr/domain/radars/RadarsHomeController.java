package com.a5lab.tabr.domain.radars;

import jakarta.validation.Valid;
import java.util.List;


import com.a5lab.tabr.utils.FlashMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class RadarsHomeController {

  private final RadarService radarService;

  private final MessageSource messageSource;

  @GetMapping({"/", "/home"})
  public ModelAndView index() {
    List<Radar> radarList = radarService.findByPrimaryAndActive(true, true);
    ModelAndView modelAndView = new ModelAndView("home/index");
    if (radarList.isEmpty()) {
      modelAndView.addObject("radar", new Radar());
      modelAndView.addObject("radar_types", this.radarTypeService.findAll());
    } else {
      modelAndView.addObject("radar", radarList.get(0));
    }
    return modelAndView;
  }

  @PostMapping(value = "/home/create")
  public ModelAndView create(@Valid Radar radar, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("home/index");
      modelAndView.addObject("radar", radar);
      return modelAndView;
    }
    radarService.save(radar);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("radar.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/home");
  }

}
