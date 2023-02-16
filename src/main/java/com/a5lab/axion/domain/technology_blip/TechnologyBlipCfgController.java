package com.a5lab.axion.domain.technology_blip;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.utils.FlashMessages;


@Controller
@RequestMapping("/settings/technology_blips")
@RequiredArgsConstructor
public class TechnologyBlipCfgController {

  private final TechnologyBlipService technologyBlipService;
  private final RadarService radarService;
  private final TechnologyService technologyService;
  private final SegmentService segmentService;
  private final RingService ringService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index(@RequestParam(defaultValue = "${application.paging.page}") int page,
                            @RequestParam(defaultValue = "${application.paging.size}") int size,
                            @RequestParam(defaultValue = "ring.title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);

    ModelAndView modelAndView = new ModelAndView("settings/technology_blips/index");
    Page<TechnologyBlipDto> blipDtoPage =
        technologyBlipService.findAll(PageRequest.of(page - 1, size, Sort.by(order)));
    modelAndView.addObject("technologyBlipDtoPage", blipDtoPage);

    int totalPages = blipDtoPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      modelAndView.addObject("pageNumbers", pageNumbers);
    }
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<TechnologyBlipDto> blipRecord = technologyBlipService.findById(id);
    if (blipRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/technology_blips/show");
      modelAndView.addObject("technologyBlipDto", blipRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("technology_blip.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/technology_blips/add");
    modelAndView.addObject("technologyBlipDto", new TechnologyBlipDto());
    modelAndView.addObject("radarDtos", this.radarService.findAll());
    modelAndView.addObject("technologyDtos", this.technologyService.findAll());
    modelAndView.addObject("segmentDtos", this.segmentService.findAll());
    modelAndView.addObject("ringDtos", this.ringService.findAll());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid TechnologyBlipDto technologyBlipDto,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/technology_blips/add");
      modelAndView.addObject("technologyBlipDto", technologyBlipDto);
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      modelAndView.addObject("technologyDtos", this.technologyService.findAll());
      modelAndView.addObject("segmentDtos", this.segmentService.findAll());
      modelAndView.addObject("ringDtos", this.ringService.findAll());
      return modelAndView;
    }

    try {
      technologyBlipService.save(technologyBlipDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("technology_blip.flash.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    } catch (DataIntegrityViolationException e) {
      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("technology_blip.flash.error.exception", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    }
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<TechnologyBlipDto> blipDto = technologyBlipService.findById(id);
    if (blipDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/technology_blips/edit");
      modelAndView.addObject("technologyBlipDto", blipDto.get());
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      modelAndView.addObject("technologyDtos", this.technologyService.findAll());
      modelAndView.addObject("segmentDtos", this.segmentService.findAll());
      modelAndView.addObject("ringDtos", this.ringService.findAll());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("technology_blip.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid TechnologyBlipDto technologyBlipDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/technology_blips/edit");
      modelAndView.addObject("technologyBlipDto", technologyBlipDto);
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      modelAndView.addObject("technologyDtos", this.technologyService.findAll());
      modelAndView.addObject("segmentDtos", this.segmentService.findAll());
      modelAndView.addObject("ringDtos", this.ringService.findAll());
      return modelAndView;
    }

    try {
      technologyBlipService.save(technologyBlipDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("technology_blip.flash.info.updated", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    } catch (DataIntegrityViolationException e) {
      // Redirect
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("technology_blip.flash.error.exception", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/technology_blips");
    }
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    technologyBlipService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("technology_blip.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/blips";
  }
}
