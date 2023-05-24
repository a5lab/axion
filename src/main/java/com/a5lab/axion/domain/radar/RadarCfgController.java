package com.a5lab.axion.domain.radar;

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

import com.a5lab.axion.domain.radar_type.RadarTypeService;
import com.a5lab.axion.utils.FlashMessages;


@Controller
@RequestMapping("/settings/radars")
@RequiredArgsConstructor
public class RadarCfgController {
  public static final String UC_RADARS_TITLE = "UC_RADARS_TITLE";

  private final RadarService radarService;

  private final RadarTypeService radarTypeService;

  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index(@Valid RadarFilter radarFilter, BindingResult bindingResult,
                            @RequestParam(defaultValue = "${application.paging.page}") int page,
                            @RequestParam(defaultValue = "${application.paging.size}") int size,
                            @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);

    ModelAndView modelAndView = new ModelAndView("settings/radars/index");
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, PageRequest.of(page - 1, size, Sort.by(order)));
    modelAndView.addObject("radarDtoPage", radarDtoPage);
    modelAndView.addObject("radarFilter", radarFilter);
    modelAndView.addObject("currentPage", radarDtoPage.getNumber() + 1);
    modelAndView.addObject("pageSize", size);
    modelAndView.addObject("sortField", sort[0]);
    modelAndView.addObject("sortDirection", sort[1]);
    modelAndView.addObject("reverseSortDirection", sort[1].equals("asc") ? "desc" : "asc");

    int totalPages = radarDtoPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      modelAndView.addObject("pageNumbers", pageNumbers);
    }
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<RadarDto> radarRecord = radarService.findById(id);
    if (radarRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/radars/show");
      modelAndView.addObject("radarDto", radarRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("radar.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/radars");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/radars/add");
    modelAndView.addObject("radarDto", new RadarDto());
    modelAndView.addObject("radar_types", radarTypeService.findAll());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid RadarDto radarDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/radars/add");
      modelAndView.addObject("radarDto", radarDto);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }
    try {
      radarService.save(radarDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/radars");
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage().contains(UC_RADARS_TITLE)) {
        bindingResult.rejectValue("title", "title_isy_taken", "this title is already taken");
      } else {
        bindingResult.reject("unknown_data_integrity_error", "unknown data integrity error");
      }
      // Show form again
      ModelAndView modelAndView = new ModelAndView("settings/radars/add");
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<RadarDto> radarDto = radarService.findById(id);
    if (radarDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/radars/edit");
      modelAndView.addObject("radarDto", radarDto.get());
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("radar.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/radars");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid RadarDto radarDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/radars/edit");
      modelAndView.addObject("radarDto", radarDto);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }

    try {
      radarService.save(radarDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("radar.flash.info.updated", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/radars");
    } catch (DataIntegrityViolationException e) {
      if (e.getMessage().contains(UC_RADARS_TITLE)) {
        bindingResult.rejectValue("title", "title_is_taken", "this title is already taken");
      } else {
        bindingResult.reject("unknown_data_integrity_error", "unknown data integrity error");
      }
      // Show form again
      ModelAndView modelAndView = new ModelAndView("settings/radars/edit");
      modelAndView.addObject("radarDto", radarDto);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    radarService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("radar.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/radars";
  }
}
