package com.a5lab.tabr.domain.rings;

import jakarta.validation.Valid;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.utils.FlashMessages;


@Controller
@RequestMapping("/settings/rings")
@RequiredArgsConstructor
public class RingsCfgController {

  private final RingService ringService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("settings/rings/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("rings",
        ringService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<RingDto> ringRecord = ringService.findById(id);
    if (ringRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/rings/show");
      modelAndView.addObject("ringDto", ringRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("ring.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/rings");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/rings/add");
    modelAndView.addObject("ringDto", new RingDto());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid RingDto ringDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/rings/add");
      modelAndView.addObject("ringDto", ringDto);
      return modelAndView;
    }
    ringService.save(ringDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("ring.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/rings");
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<RingDto> ringDto = ringService.findById(id);
    if (ringDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/rings/edit");
      modelAndView.addObject("ringDto", ringDto.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("ring.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/rings");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid RingDto ringDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/rings/edit");
      modelAndView.addObject("ringDto", ringDto);
      return modelAndView;
    }
    ringService.save(ringDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("ring.flash.info.updated", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/rings");
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    ringService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("ring.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/rings";
  }
}
