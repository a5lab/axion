package com.a5lab.axion.domain.wizard.processors;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.stream.Collectors;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.wizard.WizardDto;

public abstract class AbstractRadarProcessor implements RadarProcessor {

  protected final ApplicationContext applicationContext;

  protected final RadarService radarService;

  protected final WizardDto wizardDto;

  protected RadarDto radarDto;


  public AbstractRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    this.applicationContext = applicationContext;
    this.wizardDto = wizardDto;

    // Create radar service base on application context
    radarService =  applicationContext.getBean(RadarService.class);
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
      radar.setPrimary(Boolean.parseBoolean(record[2]));
      radar.setActive(Boolean.parseBoolean(record[3]));
      this.radarDto = this.radarService.save(radar);
    }
  }

  public void activateRadar() {
    this.radarDto.setActive(true);
    this.radarService.save(this.radarDto);
  }
}
