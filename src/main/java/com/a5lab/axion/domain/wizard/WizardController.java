package com.a5lab.axion.domain.wizard;

import jakarta.validation.Valid;
import java.util.Optional;

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

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeService;
import com.a5lab.axion.utils.FlashMessages;

@Controller
@RequestMapping("/wizard")
@RequiredArgsConstructor
public class WizardController {

  private final WizardService wizardService;

  private final RadarTypeService radarTypeService;

  private final MessageSource messageSource;

  @GetMapping("/add")
  public ModelAndView add() {
    // Create wizard dto
    WizardDto wizardDto = new WizardDto();
    Optional<RadarTypeDto> radarTypeDto = radarTypeService.findByCode(RadarType.TECHNOLOGY_RADAR);
    if (radarTypeDto.isPresent()) {
      wizardDto.setRadarTypeId(radarTypeDto.get().getId());
      wizardDto.setRadarTypeCode(radarTypeDto.get().getCode());
      wizardDto.setRadarTypeTitle(radarTypeDto.get().getTitle());
    }

    //
    ModelAndView modelAndView = new ModelAndView("wizard/add");
    modelAndView.addObject("wizard", wizardDto);
    modelAndView.addObject("radar_types", radarTypeService.findAll());
    return modelAndView;
  }


  @PostMapping(value = "/create")
  public ModelAndView create(@Valid WizardDto wizardDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("wizard/add");
      modelAndView.addObject("wizard", wizardDto);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }

    try {
      // Create wizard dto
      Optional<RadarTypeDto> radarTypeDto = radarTypeService.findById(wizardDto.getRadarTypeId());
      if (radarTypeDto.isPresent()) {
        wizardDto.setRadarTypeCode(radarTypeDto.get().getCode());
        wizardDto.setRadarTypeTitle(radarTypeDto.get().getTitle());
      }

      wizardService.createRadarEnv(wizardDto);

      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");

    } catch (Exception e) {
      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("radar.flash.error.exception", new Object[] {e.getMessage().toLowerCase()},
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");
    }
  }
}


