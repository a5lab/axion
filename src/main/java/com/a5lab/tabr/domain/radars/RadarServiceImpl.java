package com.a5lab.tabr.domain.radars;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import com.a5lab.tabr.domain.radar_types.RadarType;
import com.a5lab.tabr.domain.rings.Ring;

@RequiredArgsConstructor
@Service
@Transactional
public class RadarServiceImpl implements RadarService {
  private final RadarRepository radarRepository;

  @Override
  @Transactional(readOnly = true)
  public Collection<Radar> findAll() {
    return radarRepository.findAll(Sort.by(Sort.Direction.ASC, "title"));
  }


  @Override
  @Transactional(readOnly = true)
  public Page<Radar> findAll(Pageable pageable) {
    return radarRepository.findAll(pageable);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Radar> findById(Long id) {
    return radarRepository.findById(id);
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Radar> findByPrimary(boolean primary) {
    List<Radar> radarList = radarRepository.findByPrimary(primary);
    if (radarList.isEmpty()) {
      return Optional.empty();
    } else {
      return Optional.of(radarList.get(0));
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<Radar> findByPrimaryAndActive(boolean primary, boolean active) {
    return radarRepository.findByPrimaryAndActive(primary, active);
  }


  @Override
  @Transactional
  public Radar save(Radar radar) {
    return radarRepository.save(radar);
  }

  @Override
  @Transactional
  public void deleteById(Long id) {
    radarRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Radar createRadarEnv(Radar radar) {
    try {
      this.save(radar);
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
      return radar;
    } catch (Exception e) {
      return null;
    }
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
