package com.a5lab.axion.domain.ring;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ValidationException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.domain.AbstractRepositoryTests;

class RingRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private RingRepository ringRepository;

  @Test
  void shouldSaveRingWithAllFields() {
    // TODO: fix it, create radar before
    /*
    final Ring r = new Ring();
    r.setTitle("TEST");
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());
    Assertions.assertNotNull(r.getCreatedBy());
    Assertions.assertNotNull(r.getCreatedDate());
    Assertions.assertNotNull(r.getLastModifiedBy());
    Assertions.assertNotNull(r.getLastModifiedDate());
     */
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
    /* TODO: uncomment: radar id should be created
    final Ring r = new Ring();
    r.setTitle("MY");
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());
    var id = r.getId();

    Assertions.assertTrue(ringRepository.findById(id).isPresent());
     */
  }

  @Test
  void shouldFindSavedRingByTitle() {
    /* TODO: uncomment: radar id should be created
    String title = "SUPER";
    final Ring r = new Ring();
    r.setTitle(title);
    r.setDescription("Very good description for Ring");

    Assertions.assertNull(r.getId());
    ringRepository.saveAndFlush(r);
    Assertions.assertNotNull(r.getId());

    // todo: use service (not repository?)
    Assertions.assertTrue(ringRepository.findByTitle(title).isPresent());
     */
  }

}