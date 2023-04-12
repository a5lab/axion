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
import org.springframework.data.domain.Sort;

class RingServiceTests extends AbstractServiceTests {
  private RingRepository ringRepository = Mockito.mock(RingRepository.class);

  private RingMapper ringMapper = Mappers.getMapper(RingMapper.class);

  private RingService ringService = new RingServiceImpl(ringRepository, ringMapper);

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
  void shouldFindByIdRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("my title");
    ring.setDescription("my description");
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
    ring.setTitle("my title");
    ring.setDescription("my description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringRepository.findByTitle(ring.getTitle())).thenReturn(Optional.of(ring));

    Optional<Ring> ringOptional = ringService.findByTitle(ring.getTitle());
    Assertions.assertTrue(ringOptional.isPresent());
    Assertions.assertEquals(ring.getId(), ringOptional.get().getId());
    Assertions.assertEquals(ring.getTitle(), ringOptional.get().getTitle());
    Assertions.assertEquals(ring.getDescription(), ringOptional.get().getDescription());
    Assertions.assertEquals(ring.getColor(), ringOptional.get().getColor());
    Assertions.assertEquals(ring.getPosition(), ringOptional.get().getPosition());

    Mockito.verify(ringRepository).findByTitle(ring.getTitle());
  }

  @Test
  void shouldSaveRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("my title");
    ring.setDescription("my description");
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

  @Test
  void shouldDeleteRing() {
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("my title");
    ring.setDescription("my description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.doAnswer((i) -> null).when(ringRepository).deleteById(ring.getId());

    ringService.deleteById(ring.getId());
    Mockito.verify(ringRepository).deleteById(ring.getId());
  }
}
