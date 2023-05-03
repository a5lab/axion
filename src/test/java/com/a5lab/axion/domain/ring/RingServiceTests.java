package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractServiceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

class RingServiceTests extends AbstractServiceTests {
  private final RingRepository ringRepository = Mockito.mock(RingRepository.class);

  private final RingMapper ringMapper = Mappers.getMapper(RingMapper.class);

  private final RingService ringService = new RingServiceImpl(ringRepository, ringMapper);

  @Test
  void shouldFindAllRings() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);

    List<Ring> ringList = List.of(ring);
    Mockito.when(ringRepository.findAll(any(Sort.class))).thenReturn(ringList);

    Collection<RingDto> ringDtoCollection = ringService.findAll();
    Assertions.assertEquals(1, ringDtoCollection.size());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getId(), ring.getId());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getTitle(), ring.getTitle());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getDescription(), ring.getDescription());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getColor(), ring.getColor());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getPosition(), ring.getPosition());

  }

  @Test
  void shouldFindAllRingsWithFilter() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("My color");
    ring.setPosition(0);
    ring.setActive(true);

    List<Ring> ringList = List.of(ring);
    Page<Ring> page = new PageImpl<>(ringList);
    Mockito.when(ringRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

    RingFilter ringFilter = new RingFilter();
    Pageable pageable = PageRequest.of(0, 10, Sort.by("title,asc"));
    Page<RingDto> ringDtoPage = ringService.findAll(ringFilter, pageable);
    Assertions.assertEquals(1, ringDtoPage.getSize());
    Assertions.assertEquals(0, ringDtoPage.getNumber());
    Assertions.assertEquals(1, ringDtoPage.getTotalPages());
    Assertions.assertEquals(ringDtoPage.iterator().next().getId(), ring.getId());
    Assertions.assertEquals(ringDtoPage.iterator().next().getTitle(), ring.getTitle());
    Assertions.assertEquals(ringDtoPage.iterator().next().getDescription(), ring.getDescription());

    // Mockito.verify(tenantRepository).findAll(Specification.allOf((root, query, criteriaBuilder) -> null), pageable);
  }

  @Test
  void shouldFindByIdRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringRepository.findById(ring.getId())).thenReturn(Optional.of(ring));

    Optional<RingDto> ringDtoOptional = ringService.findById(ring.getId());
    Assertions.assertTrue(ringDtoOptional.isPresent());
    Assertions.assertEquals(ring.getId(), ringDtoOptional.get().getId());
    Assertions.assertEquals(ring.getTitle(), ringDtoOptional.get().getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDtoOptional.get().getDescription());

    Mockito.verify(ringRepository).findById(ring.getId());
  }

  @Test
  void shouldFindByTitleRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringRepository.findByTitle(ring.getTitle())).thenReturn(Optional.of(ring));

    Optional<RingDto> ringDtoOptional = ringService.findByTitle(ring.getTitle());
    Assertions.assertTrue(ringDtoOptional.isPresent());
    Assertions.assertEquals(ring.getId(), ringDtoOptional.get().getId());
    Assertions.assertEquals(ring.getTitle(), ringDtoOptional.get().getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDtoOptional.get().getDescription());
    Assertions.assertEquals(ring.getColor(), ringDtoOptional.get().getColor());
    Assertions.assertEquals(ring.getPosition(), ringDtoOptional.get().getPosition());

    Mockito.verify(ringRepository).findByTitle(ring.getTitle());
  }

  /* TODO: fix it
  @Test
  void shouldSaveRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringRepository.save(any())).thenReturn(ring);

    RingDto ringDto = ringService.save(ringMapper.toDto(ring));
    Assertions.assertEquals(ring.getId(), ringDto.getId());
    Assertions.assertEquals(ring.getTitle(), ringDto.getTitle());
    Assertions.assertEquals(ring.getDescription(), ringDto.getDescription());

    Mockito.verify(ringRepository).save(any());
  }

   */

  @Test
  void shouldDeleteRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("My title");
    ring.setDescription("My description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.doAnswer((i) -> null).when(ringRepository).deleteById(ring.getId());

    ringService.deleteById(ring.getId());
    Mockito.verify(ringRepository).deleteById(ring.getId());
  }
}
