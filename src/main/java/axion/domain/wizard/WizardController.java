package axion.domain.wizard;

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

import axion.domain.radar_type.RadarTypeService;
import axion.utils.FlashMessages;

@Controller
@RequestMapping("/wizard")
@RequiredArgsConstructor
public class WizardController {

  private final WizardService wizardService;

  private final RadarTypeService radarTypeService;

  private final MessageSource messageSource;

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("wizard/add");
    modelAndView.addObject("wizard", new Wizard());
    modelAndView.addObject("radar_types", radarTypeService.findAll());
    return modelAndView;
  }


  @PostMapping(value = "/create")
  public ModelAndView create(@Valid Wizard wizard, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("wizard/add");
      modelAndView.addObject("wizard", new Wizard());
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }

    try {
      wizardService.createRadarEnv(wizard);

      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");

    } catch (Exception e) {
      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("radar.flash.error.exception", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/home");
    }
  }

}
