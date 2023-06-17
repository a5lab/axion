package com.a5lab.axion.domain.wizard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.stream.Collectors;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.util.ResourceUtils;

import com.a5lab.axion.domain.radar.RadarDto;

public abstract class AbstractRadarProcessor implements RadarProcessor {

  protected WizardServiceImpl wizardService;

  protected WizardDto wizardDto;

  protected RadarDto radarDto;

  public AbstractRadarProcessor(WizardServiceImpl wizardService, WizardDto wizardDto) {
    this.wizardService = wizardService;
    this.wizardDto = wizardDto;
  }

  public void createRadar() throws Exception {
    // Read radars
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/radars_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      RadarDto radar = new RadarDto();
      radar.setRadarTypeId(wizardDto.getRadarType().getId());
      radar.setRadarTypeTitle(wizardDto.getRadarType().getTitle());
      radar.setTitle(record[0]);
      radar.setDescription(record[1]);
      radar.setPrimary(Boolean.valueOf(record[2]));
      radar.setActive(Boolean.valueOf(record[3]));
      this.radarDto = wizardService.radarService.save(radar);
    }
  }

  public void activateRadar() throws Exception {
    this.radarDto.setActive(true);
    this.wizardService.radarService.save(this.radarDto);
  }
}
