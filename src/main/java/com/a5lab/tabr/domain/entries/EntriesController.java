package com.a5lab.tabr.domain.entries;

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
@RequestMapping("/settings/entities")
@RequiredArgsConstructor
public class EntriesController {

  private final EntryService entityService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("settings/entities/index");
    // We need to replace it with proper values for PageRequest.of() coming from ui
    // See https://github.com/a5lab/tabr/issues/112
    modelAndView.addObject("entities",
        entityService.findAll(Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

  @GetMapping("/show/{id}")
  public ModelAndView show(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<EntryDto> entityRecord = entityService.findById(id);
    if (entityRecord.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/entities/show");
      modelAndView.addObject("entityDto", entityRecord.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("entity.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/entities");
    }
  }

  @GetMapping("/add")
  public ModelAndView add() {
    ModelAndView modelAndView = new ModelAndView("settings/entities/add");
    modelAndView.addObject("entityDto", new EntryDto());
    return modelAndView;
  }

  @PostMapping(value = "/create")
  public ModelAndView create(@Valid EntryDto entryDto, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/entities/add");
      modelAndView.addObject("entityDto", entryDto);
      return modelAndView;
    }
    entityService.save(entryDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entity.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/entities");
  }

  @GetMapping(value = "/edit/{id}")
  public ModelAndView edit(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    Optional<EntryDto> entityDto = entityService.findById(id);
    if (entityDto.isPresent()) {
      ModelAndView modelAndView = new ModelAndView("settings/entities/edit");
      modelAndView.addObject("entityDto", entityDto.get());
      return modelAndView;
    } else {
      redirectAttributes.addFlashAttribute(FlashMessages.ERROR,
          messageSource.getMessage("entity.flash.error.invalid_id", null,
              LocaleContextHolder.getLocale()));
      return new ModelAndView("redirect:/settings/entities");
    }
  }

  @PostMapping("/update")
  public ModelAndView update(@Valid EntryDto entryDto,
                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("settings/entities/edit");
      modelAndView.addObject("entityDto", entryDto);
      return modelAndView;
    }
    entityService.save(entryDto);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entity.flash.info.updated", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/settings/entities");
  }

  @GetMapping(value = "/delete/{id}")
  public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
    entityService.deleteById(id);
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("entity.flash.info.deleted", null,
            LocaleContextHolder.getLocale()));
    return "redirect:/settings/entities";
  }
}
