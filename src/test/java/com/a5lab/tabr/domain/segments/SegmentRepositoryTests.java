package com.a5lab.tabr.domain.segments;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.a5lab.tabr.AbstractRepositoryTests;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SegmentRepositoryTests extends AbstractRepositoryTests {

  @Autowired
  private SegmentRepository segmentRepository;

  @Test
  void shouldSaveSegmentWithTitleAndDescription() {
    final Segment s = new Segment();
    s.setTitle("My new test Segment");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    segmentRepository.saveAndFlush(s);
    Assertions.assertNotNull(s.getId());
    Assertions.assertNotNull(s.getCreatedBy());
    Assertions.assertNotNull(s.getCreatedDate());
    Assertions.assertNotNull(s.getLastModifiedBy());
    Assertions.assertNotNull(s.getLastModifiedDate());
  }

  @Test
  void shouldFailOnNullTitle() {
    final Segment s = new Segment();
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnNullDescription() {
    final Segment s = new Segment();
    s.setTitle("My new test Segment");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyTitle() {
    final Segment s = new Segment();
    s.setTitle("");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceTitle() {
    final Segment s = new Segment();
    s.setTitle(" ");
    s.setDescription("My awesome description");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnEmptyDescription() {
    final Segment s = new Segment();
    s.setTitle("Hello");
    s.setDescription("");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldFailOnWhiteSpaceDescription() {
    final Segment s = new Segment();
    s.setTitle("Hello");
    s.setDescription(" ");

    Assertions.assertNull(s.getId());
    assertThatThrownBy(() -> segmentRepository.saveAndFlush(s))
        .isInstanceOf(ValidationException.class);
  }

}