package com.a5lab.axion.domain.segment;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class SegmentRepositoryTests extends AbstractRepositoryTests {
  @Autowired
  private RadarRepository radarRepository;

  @Autowired
  private RadarTypeRepository radarTypeRepository;

  @Autowired
  private SegmentRepository segmentRepository;

  @Test
  void shouldSaveSegmentWithAllFields() {
    final RadarType radarType = new RadarType();
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");
    radarTypeRepository.saveAndFlush(radarType);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radar title");
    radar.setDescription("My radar description");
    radar.setPrimary(true);
    radar.setActive(false);
    radarRepository.saveAndFlush(radar);

    final Segment segment = new Segment();
    segment.setTitle("My segment title");
    segment.setRadar(radar);
    segment.setDescription("My segment description");

    Assertions.assertNull(segment.getId());
    Segment saved = segmentRepository.saveAndFlush(segment);
    Assertions.assertNotNull(saved.getId());
    Assertions.assertNotNull(saved.getRadar());
    Assertions.assertNotNull(saved.getTitle());
    Assertions.assertNotNull(saved.getDescription());
    Assertions.assertNotNull(saved.getCreatedBy());
    Assertions.assertNotNull(saved.getCreatedDate());
    Assertions.assertNotNull(saved.getLastModifiedBy());
    Assertions.assertNotNull(saved.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Segment segment = new Segment();
    segment.setDescription("My awesome description");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Segment segment = new Segment();
    segment.setTitle("My new test Segment");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Segment segment = new Segment();
    segment.setTitle("");
    segment.setDescription("My awesome description");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Segment segment = new Segment();
    segment.setTitle(" ");
    segment.setDescription("My awesome description");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Segment segment = new Segment();
    segment.setTitle("Hello");
    segment.setDescription("");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Segment segment = new Segment();
    segment.setTitle("Hello");
    segment.setDescription(" ");

    Assertions.assertNull(segment.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(segment)).isInstanceOf(ValidationException.class);
  }
}