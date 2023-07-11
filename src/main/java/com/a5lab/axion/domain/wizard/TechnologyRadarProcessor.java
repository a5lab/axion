package com.a5lab.axion.domain.wizard;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Optional;
import java.util.stream.Collectors;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ResourceUtils;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipRepository;

public class TechnologyRadarProcessor extends AbstractRadarProcessor {

  private final RingRepository ringRepository;

  private final SegmentRepository segmentRepository;

  private final TechnologyRepository technologyRepository;

  private final TechnologyBlipRepository technologyBlipRepository;



  public TechnologyRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    super(applicationContext, wizardDto);

    // Create repositories based on application context
    ringRepository =  applicationContext.getBean(RingRepository.class);
    segmentRepository =  applicationContext.getBean(SegmentRepository.class);
    technologyRepository =  applicationContext.getBean(TechnologyRepository.class);
    technologyBlipRepository =  applicationContext.getBean(TechnologyBlipRepository.class);
  }

  @Override
  public void process() throws Exception {
    this.buildRings();
    this.buildSegments();
    this.createRadar();
    this.createTechnologies();
    this.createTechnologyBlips();
  }

  public void buildRings() throws Exception {
    // Read rings
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/rings_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      Ring ring = new Ring();
      ring.setRadar(radar);
      ring.setTitle(record[0]);
      ring.setDescription(record[1]);
      ring.setPosition(Integer.parseInt(record[2]));
      ring.setColor(record[3]);
      if (radar.getRingList() == null) {
        radar.setRingList(new LinkedList<>());
      }
      radar.getRingList().add(ring);
    }
  }

  private void buildSegments() throws Exception {
    // Read segments
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/segments_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      Segment segment = new Segment();
      segment.setRadar(radar);
      segment.setTitle(record[0]);
      segment.setDescription(record[1]);
      segment.setPosition(Integer.parseInt(record[2]));
      if (radar.getSegmentList() == null) {
        radar.setSegmentList(new LinkedList<>());
      }
      radar.getSegmentList().add(segment);
    }
  }

  public void createRadar() throws Exception {
    // Find radar type
    Optional<RadarType> radarTypeOptional = this.radarTypeRepository.findById(wizardDto.getRadarTypeId());

    // Read radars
    URL url = ResourceUtils.getURL("classpath:database/datasets/technology_radar/radars_en.csv");
    String fileContent = new BufferedReader(new InputStreamReader(url.openStream())).lines()
        .collect(Collectors.joining("\n"));

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();
    while ((record = csvReader.readNext()) != null) {
      radar.setRadarType(radarTypeOptional.get());
      radar.setTitle(record[0]);
      radar.setDescription(record[1]);
      radar.setPrimary(Boolean.parseBoolean(record[2]));
      radar.setActive(Boolean.parseBoolean(record[3]));
      this.radarRepository.save(radar);
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
      Technology technology = new Technology();
      technology.setTitle(record[0]);
      technology.setWebsite(record[1]);
      technology.setDescription(record[2]);

      // Create only if not exists
      if (this.technologyRepository.findByTitle(technology.getTitle()).isEmpty()) {
        this.technologyRepository.save(technology);
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

      TechnologyBlip technologyBlip = new TechnologyBlip();
      technologyBlip.setRadar(radar);
      technologyBlip.setRing(this.ringRepository.findByTitle(ringTitle).get());
      technologyBlip.setSegment(this.segmentRepository.findByTitle(segmentTitle).get());
      technologyBlip.setTechnology(this.technologyRepository.findByTitle(technologyTitle).get());
      this.technologyBlipRepository.save(technologyBlip);
    }
  }
}
