package com.a5lab.tabr.domain.blips;

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

import com.a5lab.tabr.domain.entries.EntryService;
import com.a5lab.tabr.domain.radars.RadarService;
import com.a5lab.tabr.domain.rings.RingService;
import com.a5lab.tabr.domain.segments.SegmentService;
import com.a5lab.tabr.utils.FlashMessages;



@Controller
@RequestMapping("/settings/blips")
@RequiredArgsConstructor
public class BlipsController {

  private final BlipService blipService;
  private final RadarService radarService;
  private final EntryService entryService;
  private final SegmentService segmentService;
  private final RingService ringService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("settings/blips/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("blips",
        blipService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<BlipDto> blipRecord = blipService.findById(id);
    if (blipRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/blips/show");
      modelAndView.addObject("blipDto", blipRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("blip.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/blips");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/blips/add");
    modelAndView.addObject("blipDto", new BlipDto());
    modelAndView.addObject("radars", this.radarService.findAll());
    modelAndView.addObject("entries", this.entryService.findAll());
    modelAndView.addObject("segments", this.segmentService.findAll());
    modelAndView.addObject("rings", this.ringService.findAll());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid BlipDto blipDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/blips/add");
      modelAndView.addObject("blipDto", blipDto);
      modelAndView.addObject("radars", this.radarService.findAll());
      modelAndView.addObject("entries", this.entryService.findAll());
      modelAndView.addObject("segments", this.segmentService.findAll());
      modelAndView.addObject("rings", this.ringService.findAll());
      return modelAndView;
    }
    blipService.save(blipDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("blip.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/blips");
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<BlipDto> blipDto = blipService.findById(id);
    if (blipDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/blips/edit");
      modelAndView.addObject("blipDto", blipDto.get());
      modelAndView.addObject("radars", this.radarService.findAll());
      modelAndView.addObject("entries", this.entryService.findAll());
      modelAndView.addObject("segments", this.segmentService.findAll());
      modelAndView.addObject("rings", this.ringService.findAll());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("blip.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/blips");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid BlipDto blipDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/blips/edit");
      modelAndView.addObject("blipDto", blipDto);
      modelAndView.addObject("radars", this.radarService.findAll());
      modelAndView.addObject("entries", this.entryService.findAll());
      modelAndView.addObject("segments", this.segmentService.findAll());
      modelAndView.addObject("rings", this.ringService.findAll());
      return modelAndView;
    }
    blipService.save(blipDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("blip.flash.info.updated", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/blips");
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    blipService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("blip.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/blips";
  }
}
