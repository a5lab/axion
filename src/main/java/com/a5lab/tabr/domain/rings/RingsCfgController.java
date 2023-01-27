package com.a5lab.tabr.domain.rings;

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

import com.a5lab.tabr.utils.FlashMessages;


@Controller
@RequestMapping("/settings/rings")
@RequiredArgsConstructor
public class RingsCfgController {

  private final RingService ringService;
  private final MessageSource messageSource;

  @GetMapping("")
  public ModelAndView index(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);

    ModelAndView modelAndView = new ModelAndView("settings/rings/index");
    Page<RingDto> ringDtoPage =
        ringService.findAll(PageRequest.of(page - 1, size, Sort.by(order)));
    modelAndView.addObject("ringDtoPage", ringDtoPage);

    int totalPages = ringDtoPage.getTotalPages();
    if (totalPages > 0) {
      List<Integer> pageNumbers =
          IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
      modelAndView.addObject("pageNumbers", pageNumbers);
    }
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
