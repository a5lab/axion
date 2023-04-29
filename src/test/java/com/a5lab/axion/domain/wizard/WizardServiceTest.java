package com.a5lab.axion.domain.wizard;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarMapper;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar.RadarServiceImpl;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.tenant.TenantMapper;
import com.a5lab.axion.domain.tenant.TenantRepository;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.util.ResourceUtils;

public class WizardServiceTest extends AbstractServiceTests {

  private final RadarRepository radarRepository = Mockito.mock(RadarRepository.class);

  private final RadarMapper radarMapper = Mappers.getMapper(RadarMapper.class);

  private final RadarService radarService = new RadarServiceImpl(radarRepository, radarMapper);

  /*
  @Test
  void shouldCreateRadarEnv() {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final Wizard wizard = new Wizard(radarType);

    final Radar radar = new Radar();
    radar.setId(2L);
    radar.setRadarType(radarType);
  }


  @Test
  TODO: test for createRadar
  void shouldCreateRadar() throws Exception {
    String[] file = {
        "title|description|is_primary|is_active_|created_by|last_modified_by",
        "My Radar|My radar for organization|true|false|axion|axion"
    };
    String fileContent = String.join("\n", file);

    CSVReader mock = Mockito.mock(CSVReader.class);

    Mockito.doAnswer((i) -> null).when(mock.readNext());

    String[] record = null;
    CSVReader csvReader = new CSVReaderBuilder(new StringReader(fileContent))
        .withCSVParser(new CSVParserBuilder().withSeparator('|').build())
        .withSkipLines(1).build();

    CSVReader reader = new CSVReader();
    try {
      csvReader.readNext();
      /*
      while ((record == csvReader.readNext()) != null) {

        final Wizard wizard = new Wizard();

        final Radar radar = new Radar();
        radar.setId(1L);
        radar.setRadarType(wizard.getRadarType());
        radar.setTitle(record[0]);
        radar.setDescription(record[1]);
        radar.setPrimary(Boolean.valueOf(record[2]));
        radar.setActive(Boolean.valueOf(record[3]));
        radarService.save(radar);
      }

    }

  }

   */
}
