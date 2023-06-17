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

import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

public class TechnologyRadarProcessor extends AbstractRadarProcessor {

  public TechnologyRadarProcessor(WizardServiceImpl wizardService, WizardDto wizardDto) {
    super(wizardService, wizardDto);
  }

  @Override
  public void process() throws Exception {
    this.createRadar();
    this.createRings();
    this.createSegments();
    this.createTechnologies();
    this.createTechnologyBlips();
    this.activateRadar();
  }

  public void createRings() throws Exception {
    // Read rings
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/rings_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      RingDto ringDto = new RingDto();
      ringDto.setRadarId(radarDto.getId());
      ringDto.setRadarTitle(radarDto.getTitle());
      ringDto.setTitle(record[0]);
      ringDto.setDescription(record[1]);
      ringDto.setPosition(Integer.parseInt(record[2]));
      ringDto.setColor(record[3]);
      ringDto.setActive(true);
      this.wizardService.ringService.save(ringDto);
    }
  }

  private void createSegments() throws Exception {
    // Read segments
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/segments_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      SegmentDto segmentDto = new SegmentDto();
      segmentDto.setRadarId(radarDto.getId());
      segmentDto.setRadarTitle(radarDto.getTitle());
      segmentDto.setTitle(record[0]);
      segmentDto.setDescription(record[1]);
      segmentDto.setPosition(Integer.parseInt(record[2]));
      segmentDto.setActive(true);
      this.wizardService.segmentService.save(segmentDto);
    }
  }

  private void createTechnologies() throws Exception {
    // Read technology_blips
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/technologies_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      TechnologyDto technologyDto = new TechnologyDto();
      technologyDto.setTitle(record[0]);
      technologyDto.setWebsite(record[1]);
      technologyDto.setDescription(record[2]);

      // Create only if not exists
      if (this.wizardService.technologyService.findByTitle(technologyDto.getTitle()).isEmpty()) {
        technologyDto = this.wizardService.technologyService.save(technologyDto);
      }
    }
  }

  private void createTechnologyBlips() throws Exception {
    // Read technology_blips
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/technology_blips_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      // final String radarTitle = record[0];
      final String ringTitle = record[1];
      final String segmentTitle = record[2];
      final String technologyTitle = record[3];

      TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
      technologyBlipDto.setRadarId(this.radarDto.getId());
      technologyBlipDto.setRadarTitle(this.radarDto.getTitle());
      RingDto ringDto = this.wizardService.ringService.findByTitle(ringTitle).get();
      technologyBlipDto.setRingId(ringDto.getId());
      technologyBlipDto.setRingTitle(ringDto.getTitle());
      SegmentDto segmentDto = this.wizardService.segmentService.findByTitle(segmentTitle).get();
      technologyBlipDto.setSegmentId(segmentDto.getId());
      technologyBlipDto.setSegmentTitle(segmentDto.getTitle());
      TechnologyDto technologyDto = this.wizardService.technologyService.findByTitle(technologyTitle).get();
      technologyBlipDto.setTechnologyId(technologyDto.getId());
      technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
      this.wizardService.technologyBlipService.save(technologyBlipDto);
    }
  }
}
