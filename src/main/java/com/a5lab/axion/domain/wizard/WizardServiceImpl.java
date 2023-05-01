package com.a5lab.axion.domain.wizard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.stream.Collectors;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipService;

@RequiredArgsConstructor
@Service
@Transactional
public class WizardServiceImpl implements WizardService {

  private final RadarService radarService;
  private final RingService ringService;
  private final SegmentService segmentService;
  private final TechnologyService technologyService;
  private final TechnologyBlipService technologyBlipService;

  private RadarDto radarDto;

  @Override
  @Transactional
  public void createRadarEnv(WizardDto wizardDto) throws Exception {
    this.createRadar(wizardDto);
    this.createRings();
    this.createSegments();
    this.createTechnologyBlips();
    this.radarDto.setActive(true);
    this.radarService.save(this.radarDto);

    /*
    switch (radars.getRadarType().getCode()) {
      case RadarType.CAPABILITY_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radars.getRadarType().getCode());
      case RadarType.PRACTICE_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radars.getRadarType().getCode());
      case RadarType.PROCESS_RADAR:
        throw new IllegalArgumentException(
            "Not implemented yes. Radar type:" + radars.getRadarType().getCode());
      case RadarType.TECHNOLOGY_RADAR:
        this.createRings(radars);
        this.createSegments(radars);
        this.createTechnologyBlips(radars);
        break;
      default:
        throw new IllegalArgumentException(
            "Unknown radars type: " + radars.getRadarType().getCode());
    }
    */
  }

  public void createRadar(WizardDto wizardDto) throws Exception {
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
      this.radarDto = radarService.save(radar);
    }
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
      ringService.save(ringDto);
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
      segmentService.save(segmentDto);
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
      RingDto ringDto = ringService.findByTitle(ringTitle).get();
      technologyBlipDto.setRingId(ringDto.getId());
      technologyBlipDto.setRingTitle(ringDto.getTitle());
      SegmentDto segmentDto = segmentService.findByTitle(segmentTitle).get();
      technologyBlipDto.setSegmentId(segmentDto.getId());
      technologyBlipDto.setSegmentTitle(segmentDto.getTitle());
      TechnologyDto technologyDto = technologyService.findByTitle(technologyTitle).get();
      technologyBlipDto.setTechnologyId(technologyDto.getId());
      technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
      technologyBlipService.save(technologyBlipDto);
    }
  }
}
