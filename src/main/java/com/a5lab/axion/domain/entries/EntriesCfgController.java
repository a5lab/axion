package com.a5lab.axion.domain.entries;

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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.axion.utils.FlashMessages;


@Controller
@RequestMapping("/settings/entries")
@RequiredArgsConstructor
public class EntriesCfgController {

  private final EntryService entryService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index(@RequestParam(defaultValue = "${application.paging.page}") int page,
                            @RequestParam(defaultValue = "${application.paging.size}") int size,
                            @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);

    ModelAndView modelAndView = new ModelAndView("settings/entries/index");
    Page<EntryDto> entryDtoPage =
        entryService.findAll(PageRequest.of(page - 1, size, Sort.by(order)));
    modelAndView.addObject("entryDtoPage", entryDtoPage);

    int totalPages = entryDtoPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      modelAndView.addObject("pageNumbers", pageNumbers);
    }
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<EntryDto> entryRecord = entryService.findById(id);
    if (entryRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/entries/show");
      modelAndView.addObject("entryDto", entryRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("entry.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/entries");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/entries/add");
    modelAndView.addObject("entryDto", new EntryDto());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid EntryDto entryDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/entries/add");
      modelAndView.addObject("entryDto", entryDto);
      return modelAndView;
    }
    entryService.save(entryDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entry.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/entries");
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<EntryDto> entryDto = entryService.findById(id);
    if (entryDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/entries/edit");
      modelAndView.addObject("entryDto", entryDto.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("entry.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/entries");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid EntryDto entryDto,
                             BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/entries/edit");
      modelAndView.addObject("entryDto", entryDto);
      return modelAndView;
    }
    entryService.save(entryDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entry.flash.info.updated", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/entries");
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    entryService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entry.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/entries";
  }
}
