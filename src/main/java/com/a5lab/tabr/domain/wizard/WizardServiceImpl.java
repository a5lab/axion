package com.a5lab.tabr.domain.wizard;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.a5lab.tabr.domain.blips.Blip;
import com.a5lab.tabr.domain.blips.BlipService;
import com.a5lab.tabr.domain.entries.EntryService;
import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.radars.RadarService;
import com.a5lab.tabr.domain.rings.RingDto;
import com.a5lab.tabr.domain.rings.RingService;
import com.a5lab.tabr.domain.segments.SegmentDto;
import com.a5lab.tabr.domain.segments.SegmentService;

@RequiredArgsConstructor
@Service
@Transactional
public class WizardServiceImpl implements WizardService {

  private final RadarService radarService;
  private final RingService ringService;
  private final SegmentService segmentService;

  private final EntryService entryService;
  private final BlipService blipService;

  private Radar radar;

  @Override
  // how to do for debug 1000 and for production default. Use applicatin.yml?
  @Transactional(timeout = 100)
  public void createRadarEnv(Wizard wizard) throws Exception {
    this.createRadar(wizard);
    this.createRings();
    this.createSegments();
    this.createTechnologyBlips();
    this.radar.setActive(true);
    this.radarService.save(this.radar);

    /*
    switch (radar.getRadarType().getCode()) {
      case RadarType.CAPABILITY_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radar.getRadarType().getCode());
      case RadarType.PRACTICE_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radar.getRadarType().getCode());
      case RadarType.PROCESS_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radar.getRadarType().getCode());
      case RadarType.TECHNOLOGY_RADAR:
        this.createRings(radar);
        this.createSegments(radar);
        this.createTechnologyBlips(radar);
        break;
      default:
        throw new IllegalArgumentException(
            "Unknown radar type: " + radar.getRadarType().getCode());
    }
    */
  }

  public void createRadar(Wizard wizard) throws Exception {
    // Read rings
    File file =
        ResourceUtils.getFile("classpath:database/datasets/technology_radar/radars_en.csv");
    Path path = file.toPath();
    Stream<String> lines = Files.lines(path);
    String fileContent = lines.collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      Radar radar = new Radar();
      radar.setRadarType(wizard.getRadarType());
      radar.setTitle(record[0]);
      radar.setDescription(record[1]);
      radar.setPrimary(Boolean.valueOf(record[2]));
      radar.setActive(Boolean.valueOf(record[3]));
      this.radar = radarService.save(radar);
    }
  }

  public void createRings() throws Exception {
    // Read rings
    File file =
        ResourceUtils.getFile("classpath:database/datasets/technology_radar/rings_en.csv");
    Path path = file.toPath();
    Stream<String> lines = Files.lines(path);
    String fileContent = lines.collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      RingDto ringDto = new RingDto();
      ringDto.setRadar(radar);
      ringDto.setTitle(record[0]);
      ringDto.setDescription(record[1]);
      ringDto.setPosition(Integer.parseInt(record[2]));
      ringDto.setActive(true);
      ringService.save(ringDto);
    }
  }

  private void createSegments() throws Exception {
    // Read rings
    File file =
        ResourceUtils.getFile("classpath:database/datasets/technology_radar/segments_en.csv");
    Path path = file.toPath();
    Stream<String> lines = Files.lines(path);
    String fileContent = lines.collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      SegmentDto segmentDto = new SegmentDto();
      segmentDto.setRadar(radar);
      segmentDto.setTitle(record[0]);
      segmentDto.setDescription(record[1]);
      segmentDto.setPosition(Integer.parseInt(record[2]));
      segmentDto.setActive(true);
      segmentService.save(segmentDto);
    }
  }

  private void createTechnologyBlips() throws Exception {
    // Read technology_blips_en
    File file =
        ResourceUtils.getFile(
            "classpath:database/datasets/technology_radar/technology_blips_en.csv");
    Path path = file.toPath();
    Stream<String> lines = Files.lines(path);
    String fileContent = lines.collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      // final String radarTitle = record[0];
      final String ringTitle = record[1];
      final String segmentTitle = record[2];
      final String entryTitle = record[3];

      Blip blip = new Blip();
      blip.setRadar(this.radar);
      blip.setRing(ringService.findByTitle(ringTitle).get());
      blip.setSegment(segmentService.findByTitle(segmentTitle).get());
      blip.setEntry(entryService.findByTitle(entryTitle).get());
      blipService.save(blip);
    }
  }


}
