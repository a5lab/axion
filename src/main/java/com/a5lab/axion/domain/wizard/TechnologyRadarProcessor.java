package com.a5lab.axion.domain.wizard;

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

import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.Technology;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipRepository;

public class TechnologyRadarProcessor extends AbstractRadarProcessor {

  private final RingRepository ringRepository;

  private final SegmentRepository segmentRepository;

  private final TechnologyRepository technologyRepository;

  private final TechnologyBlipRepository technologyBlipRepository;

  private final RingMapper ringMapper;

  private final SegmentMapper segmentMapper;

  private final TechnologyMapper technologyMapper;

  private final TechnologyBlipMapper technologyBlipMapper;
  

  public TechnologyRadarProcessor(ApplicationContext applicationContext, WizardDto wizardDto) {
    super(applicationContext, wizardDto);

    // Create repositories based on application context
    ringRepository =  applicationContext.getBean(RingRepository.class);
    segmentRepository =  applicationContext.getBean(SegmentRepository.class);
    technologyRepository =  applicationContext.getBean(TechnologyRepository.class);
    technologyBlipRepository =  applicationContext.getBean(TechnologyBlipRepository.class);

    // Create mappers based on application context
    ringMapper =  applicationContext.getBean(RingMapper.class);
    segmentMapper =  applicationContext.getBean(SegmentMapper.class);
    technologyMapper =  applicationContext.getBean(TechnologyMapper.class);
    technologyBlipMapper =  applicationContext.getBean(TechnologyBlipMapper.class);
    
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
      this.ringRepository.save(this.ringMapper.toEntity(ringDto));
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
      this.segmentRepository.save(this.segmentMapper.toEntity(segmentDto));
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
      if (this.technologyRepository.findByTitle(technologyDto.getTitle()).isEmpty()) {
        this.technologyRepository.save(technologyMapper.toEntity(technologyDto));
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
      Ring ring = this.ringRepository.findByTitle(ringTitle).get();
      technologyBlipDto.setRingId(ring.getId());
      technologyBlipDto.setRingTitle(ring.getTitle());
      Segment segment = this.segmentRepository.findByTitle(segmentTitle).get();
      technologyBlipDto.setSegmentId(segment.getId());
      technologyBlipDto.setSegmentTitle(segment.getTitle());
      Technology technology = this.technologyRepository.findByTitle(technologyTitle).get();
      technologyBlipDto.setTechnologyId(technology.getId());
      technologyBlipDto.setTechnologyTitle(technology.getTitle());
      this.technologyBlipRepository.save(technologyBlipMapper.toEntity(technologyBlipDto));
    }
  }
}
