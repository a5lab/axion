package com.a5lab.axion.domain.segment;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.axion.domain.FlashMessages;
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar.RadarService;


@Controller
@RequestMapping("/settings/segments")
@RequiredArgsConstructor
public class SegmentCfgController {

  private final SegmentService segmentService;

  private final RadarService radarService;

  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index(@Valid SegmentFilter segmentFilter, BindingResult bindingResult,
                            @RequestParam(defaultValue = "${application.paging.page}") int page,
                            @RequestParam(defaultValue = "${application.paging.size}") int size,
                            @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);

    ModelAndView modelAndView = new ModelAndView("settings/segments/index");
    Page<SegmentDto> segmentDtoPage =
        segmentService.findAll(segmentFilter, PageRequest.of(page - 1, size, Sort.by(order)));
    modelAndView.addObject("segmentDtoPage", segmentDtoPage);
    modelAndView.addObject("segmentFilter", segmentFilter);
    modelAndView.addObject("currentPage", segmentDtoPage.getNumber() + 1);
    modelAndView.addObject("pageSize", size);
    modelAndView.addObject("sortField", sort[0]);
    modelAndView.addObject("sortDirection", sort[1]);
    modelAndView.addObject("reverseSortDirection", sort[1].equals("asc") ? "desc" : "asc");


    int totalPages = segmentDtoPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      modelAndView.addObject("pageNumbers", pageNumbers);
    }
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
          messageSource.getMessage("segment.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/segments");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/segments/add");
    modelAndView.addObject("segmentDto", new SegmentDto());
    modelAndView.addObject("radarDtos", this.radarService.findAll());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid SegmentDto segmentDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/add");
      modelAndView.addObject("segmentDto", segmentDto);
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      return modelAndView;
    }
    try {
      segmentService.save(segmentDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("segment.info.created", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/segments");
    } catch (ConstraintViolationException e) {
      // fuck
      // Add errors to fields and global
      for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
        String field = constraintViolation.getPropertyPath().toString();
        if (field.isEmpty() || field.isBlank()) {
          bindingResult.reject("validation_is_broken", constraintViolation.getMessage());
        } else {
          bindingResult.rejectValue(field, "validation_is_broken", constraintViolation.getMessage());
        }
      }

      // Show form again
      ModelAndView modelAndView = new ModelAndView("settings/segments/add");
      modelAndView.addObject("segmentDto", segmentDto);
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      return modelAndView;
    }
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<SegmentDto> segmentDto = segmentService.findById(id);
    if (segmentDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/segments/edit");
      modelAndView.addObject("segmentDto", segmentDto.get());
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("segment.error.invalid_id", null,
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
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      return modelAndView;
    }

    try {
      segmentService.save(segmentDto);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("segment.info.updated", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/segments");
    } catch (TransactionSystemException e) {
      // fuck
      if (e.getCause().getCause() instanceof ConstraintViolationException) {
        // Add errors to fields and global
        ConstraintViolationException exception = (ConstraintViolationException) e.getCause().getCause();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
          String field = constraintViolation.getPropertyPath().toString();
          if (field.isEmpty() || field.isBlank()) {
            bindingResult.reject("validation_is_broken", constraintViolation.getMessage());
          } else {
            bindingResult.rejectValue(field, "validation_is_broken", constraintViolation.getMessage());
          }
        }
      }

      // Show form again
      ModelAndView modelAndView = new ModelAndView("settings/segments/edit");
      modelAndView.addObject("segmentDto", segmentDto);
      modelAndView.addObject("radarDtos", this.radarService.findAll());
      return modelAndView;
    }
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    try {
      segmentService.deleteById(id);
      redirectAttributes.addFlashAttribute(FlashMessages.INFO,
          messageSource.getMessage("segment.info.deleted", null,
              LocaleContextHolder.getLocale()));
      return "redirect:/settings/segments";
    } catch (ValidationException e) {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR, e.getMessage());
      return "redirect:/settings/segments";
    }
  }
}
