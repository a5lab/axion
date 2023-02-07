package com.a5lab.tabr.domain.radars;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.domain.radar_types.RadarTypeService;
import com.a5lab.tabr.utils.FlashMessages;

@Controller
@RequestMapping("/wizard")
@RequiredArgsConstructor
public class RadarsWizardController {

  private final RadarService radarService;

  private final RadarTypeService radarTypeService;

  private final MessageSource messageSource;

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("wizard/add");
    modelAndView.addObject("radar", new Radar());
    modelAndView.addObject("radar_types", radarTypeService.findAll());
    return modelAndView;
  }


  @PostMapping(value = "/create")
  public ModelAndView create(@Valid Radar radar, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("wizard/add");
      modelAndView.addObject("radar", radar);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }
    radarService.save(radar);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("radar.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/home");
  }

}
