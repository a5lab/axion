package com.a5lab.tabr.domain.radars;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.a5lab.tabr.AbstractRepositoryTests;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RadarRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private RadarRepository radarRepository;

  @Test
  void shouldSaveRadarWithTitleAndDescription() {
    final Radar s = new Radar();
    s.setTitle("My new test Radar");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    radarRepository.saveAndFlush(s);
    Assertions.assertNotNull(s.getId());
    Assertions.assertNotNull(s.getCreatedBy());
    Assertions.assertNotNull(s.getCreatedDate());
    Assertions.assertNotNull(s.getLastModifiedBy());
    Assertions.assertNotNull(s.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Radar s = new Radar();
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Radar s = new Radar();
    s.setTitle("My new test Radar");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Radar s = new Radar();
    s.setTitle("");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Radar s = new Radar();
    s.setTitle(" ");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Radar s = new Radar();
    s.setTitle("Hello");
    s.setDescription("");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Radar s = new Radar();
    s.setTitle("Hello");
    s.setDescription(" ");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> radarRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

}