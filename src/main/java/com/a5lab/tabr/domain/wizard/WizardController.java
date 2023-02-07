package com.a5lab.tabr.domain.wizard;

import jakarta.validation.Valid;
import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.a5lab.tabr.domain.radar_types.RadarTypeService;
import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.radars.RadarService;
import com.a5lab.tabr.domain.rings.Ring;
import com.a5lab.tabr.utils.FlashMessages;

@Controller
@RequestMapping("/wizard")
@RequiredArgsConstructor
public class WizardController {

  private final RadarService radarService;

  private final RadarTypeService radarTypeService;

  private final MessageSource messageSource;

  @GetMapping("/add")
  public ModelAndView add() {
    try {
      // Read rings
      File file =
          ResourceUtils.getFile("classpath:database/datasets/technology_radar/rings_en.csv");
      Path path = file.toPath();
      Stream<String> lines = Files.lines(path);
      String fileContent = lines.collect(Collectors.joining("\n"));
      System.out.println(fileContent);

      String[] record = null;
      CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
          .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
          .withSkipLines(1).build();
      while ((record = csvReader.readNext()) != null) {
        Ring ring = new Ring();
        ring.setTitle(record[0]);
        ring.setDescription(record[1]);
        ring.setPosition(Integer.parseInt(record[2]));
        System.out.println(ring.toString());
      }
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    ModelAndView modelAndView = new ModelAndView("wizard/add");
    modelAndView.addObject("radar", new Radar());
    modelAndView.addObject("radar_types", radarTypeService.findAll());
    return modelAndView;
  }


  @PostMapping(value = "/create")
  public ModelAndView create(@Valid Radar radar, BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      ModelAndView modelAndView = new ModelAndView("wizard/add");
      modelAndView.addObject("radar", radar);
      modelAndView.addObject("radar_types", radarTypeService.findAll());
      return modelAndView;
    }

    // Create all data
    radarService.save(radar);

    // Redirect
    redirectAttributes.addFlashAttribute(FlashMessages.INFO,
        messageSource.getMessage("radar.flash.info.created", null,
            LocaleContextHolder.getLocale()));
    return new ModelAndView("redirect:/home");
  }

}
