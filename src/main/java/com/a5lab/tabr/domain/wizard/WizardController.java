package com.a5lab.tabr.domain.wizard;

import jakarta.validation.Valid;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.domain.radar_types.RadarType;
import com.a5lab.tabr.domain.radar_types.RadarTypeService;
import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.radars.RadarService;
import com.a5lab.tabr.domain.rings.Ring;
import com.a5lab.tabr.utils.FlashMessages;

@Controller
@RequestMapping("/wizard")
@RequiredArgsConstructor
public class WizardController {

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

    try{
      this.radarService.createRadarEnv(radar);

      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.error.exception", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");

    } catch (Exception e){
      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");
    }
  }

}
