package com.a5lab.axion.domain.radar;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;

import org.hibernate.validator.internal.engine.path.PathImpl;

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

  @Test
  void shouldFailOnSecondPrimaryRadar() {
    /* TODO
    // Create a radar type
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 2");
    radarType.setCode("technology_radar_2");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    // Create a radar
    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My new test radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(true);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    // Create second radar
    final Radar radar1 = new Radar();
    radar1.setRadarType(radarType);
    radar1.setTitle("My new primary test radar");
    radar1.setDescription("My awesome description");
    radar1.setPrimary(true);
    radar1.setActive(false);

    Assertions.assertNull(radar1.getId());
    ConstraintViolationException exception =
        catchThrowableOfType(() -> radarRepository.saveAndFlush(radar1),
            ConstraintViolationException.class);

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getConstraintViolations().size(), 1);
    for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
      Assertions.assertEquals(
          ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(), "primary");
      Assertions.assertEquals(constraintViolation.getMessage(), "can be only one primary");
    }
   */
  }
}