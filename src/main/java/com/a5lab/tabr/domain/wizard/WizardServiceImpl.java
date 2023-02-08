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

import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.radars.RadarService;
import com.a5lab.tabr.domain.rings.Ring;
import com.a5lab.tabr.domain.rings.RingService;

@RequiredArgsConstructor
@Service
@Transactional
public class WizardServiceImpl implements WizardService {

  private final RadarService radarService;
  private final RingService ringService;


  @Override
  @Transactional
  public void createRadar(Radar radar) throws Exception {
    radarService.save(radar);
    this.createRings(radar);
    this.createSegments(radar);
    this.createTechnologyBlips(radar);

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

  public void createRings(Radar radar) throws Exception {
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
      Ring ring = new Ring();
      ring.setTitle(record[0]);
      ring.setDescription(record[1]);
      ring.setPosition(Integer.parseInt(record[2]));
      System.out.println(ring.toString());
    }
  }

  private void createSegments(Radar radar) {
    System.out.println(radar);
  }

  private void createTechnologyBlips(Radar radar) {
    System.out.println(radar);
  }


}
