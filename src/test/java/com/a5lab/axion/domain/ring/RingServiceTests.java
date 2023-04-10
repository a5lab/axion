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
    Assertions.assertEquals(ringDtoCollection.iterator().next().getColor(), ring.getColor());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getPosition(), ring.getPosition());
    Assertions.assertEquals(ringDtoCollection.iterator().next().getDescription(), ring.getDescription());

  }
  /*
  @Test
  void shouldSaveRings() {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setTitle("my title");
    ringDto.setDescription("my description");
    ringDto.setColor("my color");
    ringDto.setPosition(0);
    ringDto.setActive(true);
    //    not sure for this
    Mockito.when(ringMapper.toDto(ringRepository.save(ringMapper.toEntity(ringDto)))).thenReturn(Optional.of(ringDto));
    ringService.save(ringDto);
    Mockito.verify(ringRepository).save(ringMapper.toEntity(ringDto));
  }
   */

  @Test
  void shouldFindByIdRings(){
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("my title");
    ring.setDescription("my description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    List<Ring> ringList = List.of(ring);
    Mockito.when(ringRepository.findById(ring.getId())).thenReturn(Optional.of(ring));

    ringService.findById(ring.getId());
    Mockito.verify(ringRepository).findById(ring.getId());
  }

  @Test
  void shouldFindByTitleRings(){
    final Ring ring = new Ring();
    ring.setId(10L);
    ring.setTitle("my title");
    ring.setDescription("my description");
    ring.setColor("my color");
    ring.setPosition(0);
    ring.setActive(true);

    Mockito.when(ringRepository.findByTitle(ring.getTitle())).thenReturn(Optional.of(ring));
    ringService.findByTitle(ring.getTitle());

    Mockito.verify(ringRepository).findByTitle(ring.getTitle());

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

    List<Ring> ringList = List.of(ring);
    Mockito.when(ringRepository.findById(ring.getId())).thenReturn(Optional.of(ring));

    ringService.deleteById(ring.getId());
    Mockito.verify(ringRepository).deleteById(ring.getId());

  }
}
