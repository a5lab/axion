package com.a5lab.axion.domain.radar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;


import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class RadarRepositoryTests extends AbstractRepositoryTests {
  @Autowired
  private RadarRepository radarRepository;

  private RadarTypeRepository radarTypeRepository;

  @Test
  void shouldSaveRadarWithAllFields() {
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
    radar.setPrimary(false);
    radar.setActive(false);

    Assertions.assertNull(radar.getId());
    radarRepository.saveAndFlush(radar);
    Assertions.assertNotNull(radar.getId());
    Assertions.assertNotNull(radar.getRadarType());
    Assertions.assertNotNull(radar.getTitle());
    Assertions.assertNotNull(radar.getDescription());
    Assertions.assertNotNull(radar.getCreatedBy());
    Assertions.assertNotNull(radar.getCreatedDate());
    Assertions.assertNotNull(radar.getLastModifiedBy());
    Assertions.assertNotNull(radar.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Radar radar = new Radar();
    radar.setDescription("My awesome description");

    Assertions.assertNull(radar.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(radar))
        .isInstanceOf(ValidationException.class);
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