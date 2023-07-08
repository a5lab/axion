package com.a5lab.axion.domain.radar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class RadarRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private RadarRepository radarRepository;


  @Autowired
  private RadarTypeRepository radarTypeRepository;

  @Test
  void shouldSaveRadarWithAllFields() {
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);

    Assertions.assertNull(radar.getId());
    Radar saved = radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(saved.getId());
    Assertions.assertNotNull(saved.getRadarType());
    Assertions.assertNotNull(saved.getTitle());
    Assertions.assertNotNull(saved.getDescription());
    Assertions.assertNotNull(saved.getCreatedBy());
    Assertions.assertNotNull(saved.getCreatedDate());
    Assertions.assertNotNull(saved.getLastModifiedBy());
    Assertions.assertNotNull(saved.getLastModifiedDate());
  }

  @Test
  void shouldFindByTitleRadar() {
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);
    Radar saved = radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(saved.getId());

    List<Radar> radarList = radarRepository.findByTitle(radar.getTitle());
    Assertions.assertNotNull(radarList);
    Assertions.assertEquals(radarList.iterator().next().getTitle(), saved.getTitle(), radar.getTitle());
  }

  @Test
  void shouldFailToFindByTitleRadarDueToEmptyField() {
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);
    Radar saved = radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(saved.getId());

    List<Radar> radarList = radarRepository.findByTitle("");
    Assertions.assertTrue(radarList.isEmpty());
  }

  @Test
  void shouldFailToFindByTitleRadarDueToWhiteSpace() {
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);
    Radar saved = radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(saved.getId());

    List<Radar> radarList = radarRepository.findByTitle(" " + radar.getTitle());
    Assertions.assertTrue(radarList.isEmpty());
  }

  @Test
  void shouldFailOnNullTitle() {
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars");
    radarType.setCode("technology_radar");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(true);
    radar.setActive(false);

    Assertions.assertNull(radar.getId());
    Radar saved = radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(saved.getId());
    Assertions.assertNotNull(saved.getRadarType());
    Assertions.assertNotNull(saved.getTitle());
    Assertions.assertNotNull(saved.getDescription());
    Assertions.assertNotNull(saved.getCreatedBy());
    Assertions.assertNotNull(saved.getCreatedDate());
    Assertions.assertNotNull(saved.getLastModifiedBy());
    Assertions.assertNotNull(saved.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullDescription() {
    final Radar radar = new Radar();
    radar.setTitle("My new test Radar");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Radar radar = new Radar();
    radar.setTitle("");
    radar.setDescription("My awesome description");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Radar radar = new Radar();
    radar.setTitle(" ");
    radar.setDescription("My awesome description");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Radar radar = new Radar();
    radar.setTitle("Hello");
    radar.setDescription("");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Radar radar = new Radar();
    radar.setTitle("Hello");
    radar.setDescription(" ");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
  }
}