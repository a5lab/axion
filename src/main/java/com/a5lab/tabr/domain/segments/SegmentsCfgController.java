package com.a5lab.tabr.domain.segments;

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
@RequestMapping("/settings/segments")
@RequiredArgsConstructor
public class SegmentsCfgController {

  private final SegmentService segmentService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("settings/segments/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("segments",
        segmentService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<SegmentDto> segmentRecord = segmentService.findById(id);
    if (segmentRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/show");
      modelAndView.addObject("segmentDto", segmentRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("segment.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/segments");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/segments/add");
    modelAndView.addObject("segmentDto", new SegmentDto());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid SegmentDto segmentDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/add");
      modelAndView.addObject("segmentDto", segmentDto);
      return modelAndView;
    }
    segmentService.save(segmentDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("segment.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/segments");
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<SegmentDto> segmentDto = segmentService.findById(id);
    if (segmentDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/edit");
      modelAndView.addObject("segmentDto", segmentDto.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("segment.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/segments");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid SegmentDto segmentDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/edit");
      modelAndView.addObject("segmentDto", segmentDto);
      return modelAndView;
    }
    segmentService.save(segmentDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("segment.flash.info.updated", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/segments");
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    segmentService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("segment.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/segments";
  }
}
