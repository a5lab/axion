package com.a5lab.axion.domain.radar_type;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class RadarTypeRepositoryTests extends AbstractRepositoryTests {
  @Autowired
  private RadarTypeRepository radarTypeRepository;

  @Test
  void shouldSaveRadarWithAllFields() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars");
    radarType.setCode("technology_radar");
    radarType.setDescription("Technology radars");

    Assertions.assertNull(radarType.getId());
    radarTypeRepository.saveAndFlush(radarType);
    Assertions.assertNotNull(radarType.getId());
    Assertions.assertNotNull(radarType.getCode());
    Assertions.assertNotNull(radarType.getTitle());
    Assertions.assertNotNull(radarType.getDescription());
    Assertions.assertNotNull(radarType.getCreatedBy());
    Assertions.assertNotNull(radarType.getCreatedDate());
    Assertions.assertNotNull(radarType.getLastModifiedBy());
    Assertions.assertNotNull(radarType.getLastModifiedDate());
  }


  @Test
  void shouldFailToSaveRadarTypeDueToTitleWithRightWhiteSpace() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("My new test RadarType ");

    Assertions.assertNull(radarType.getId());
    assertThatThrownBy(() -> radarTypeRepository.saveAndFlush(radarType))
            .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailToSaveRadarTypeDueToTitleWithLeftWhiteSpace() {
    final RadarType radarType = new RadarType();
    radarType.setTitle(" My new test RadarType");

    Assertions.assertNull(radarType.getId());
    assertThatThrownBy(() -> radarTypeRepository.saveAndFlush(radarType))
            .isInstanceOf(ValidationException.class);
  }
}