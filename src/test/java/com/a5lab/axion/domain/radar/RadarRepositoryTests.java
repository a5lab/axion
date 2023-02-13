package com.a5lab.axion.domain.radar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class RadarRepositoryTests extends AbstractRepositoryTests {
  @Autowired
  private RadarRepository radarRepository;

  @Test
  void shouldSaveRadarWithAllFields() {
    /* TODO: implement
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars");
    radarType.setCode("technology_radar");
    radarType.setDescription("Technology radars");

    final Radar radars = new Radar();
    radars.setRadarType(radarType);
    radars.setTitle("My new test Radar");
    radars.setDescription("My awesome description");
    radars.setPrimary(false);

    Assertions.assertNull(radars.getId());
    radarRepository.saveAndFlush(radars);
    Assertions.assertNotNull(radars.getId());
    Assertions.assertNotNull(radars.getRadarType());
    Assertions.assertNotNull(radars.getTitle());
    Assertions.assertNotNull(radars.getDescription());
    Assertions.assertNotNull(radars.getCreatedBy());
    Assertions.assertNotNull(radars.getCreatedDate());
    Assertions.assertNotNull(radars.getLastModifiedBy());
    Assertions.assertNotNull(radars.getLastModifiedDate());
    */
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