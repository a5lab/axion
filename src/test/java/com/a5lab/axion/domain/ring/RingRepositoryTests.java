package com.a5lab.axion.domain.ring;

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

class RingRepositoryTests extends AbstractRepositoryTests {
  @Autowired
  private RadarRepository radarRepository;

  @Autowired
  private RadarTypeRepository radarTypeRepository;

  @Autowired
  private RingRepository ringRepository;

  @Test
  void shouldSaveRingWithAllFields() {
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

    final Ring ring = new Ring();
    ring.setTitle("ADOPT");
    ring.setRadar(radar);
    ring.setColor("d42d");
    ring.setDescription("Very good description for ring");

    Assertions.assertNull(ring.getId());
    Ring saved = ringRepository.saveAndFlush(ring);
    Assertions.assertNotNull(saved.getId());
    Assertions.assertNotNull(saved.getRadar());
    Assertions.assertNotNull(saved.getTitle());
    Assertions.assertNotNull(saved.getColor());
    Assertions.assertNotNull(saved.getDescription());
    Assertions.assertNotNull(saved.getCreatedBy());
    Assertions.assertNotNull(saved.getCreatedDate());
    Assertions.assertNotNull(saved.getLastModifiedBy());
    Assertions.assertNotNull(saved.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Ring r = new Ring();
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Ring r = new Ring();
    r.setTitle("");
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Ring r = new Ring();
    r.setTitle(" ");
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Ring r = new Ring();
    r.setTitle("TEST");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Ring r = new Ring();
    r.setTitle("TEST");
    r.setDescription("");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Ring r = new Ring();
    r.setTitle("TEST");
    r.setDescription(" ");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnLowerTitle() {
    final Ring r = new Ring();
    r.setTitle("test");
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    assertThatThrownBy(() -> ringRepository.saveAndFlush(r))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFindSavedRingById() {
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

    final Ring ring = new Ring();
    ring.setTitle("ADOPT");
    ring.setRadar(radar);
    ring.setColor("d42d");
    ring.setDescription("Very good description for ring");

    Assertions.assertNull(ring.getId());
    ringRepository.saveAndFlush(ring);
    Assertions.assertNotNull(ring.getId());
    var id = ring.getId();

    Assertions.assertTrue(ringRepository.findById(id).isPresent());
  }

  @Test
  void shouldFindSavedRingByTitle() {
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

    String title = "SUPER";
    final Ring ring = new Ring();
    ring.setTitle(title);
    ring.setRadar(radar);
    ring.setColor("d42d");
    ring.setDescription("Very good description for Ring");

    Assertions.assertNull(ring.getId());
    ringRepository.saveAndFlush(ring);
    Assertions.assertNotNull(ring.getId());

    // todo: use service (not repository?)
    Assertions.assertTrue(ringRepository.findByTitle(title).isPresent());
  }
}