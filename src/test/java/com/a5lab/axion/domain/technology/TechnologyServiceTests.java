package com.a5lab.axion.domain.technology;

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

class TechnologyServiceTests extends AbstractServiceTests {
  private TechnologyRepository technologyRepository = Mockito.mock(TechnologyRepository.class);

  private TechnologyMapper technologyMapper = Mappers.getMapper(TechnologyMapper.class);

  private TechnologyService technologyService = new TechnologyServiceImpl(technologyRepository, technologyMapper);

  @Test
  void shouldFindAllTechnologies() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);
    List<Technology> technologyList = List.of(technology);
    Mockito.when(technologyRepository.findAll(any(Sort.class))).thenReturn(technologyList);

    Collection<TechnologyDto> technologyDtoCollection = technologyService.findAll();
    Assertions.assertEquals(1, technologyDtoCollection.size());
    Assertions.assertEquals(technologyDtoCollection.iterator().next().getId(), technology.getId());
    Assertions.assertEquals(technologyDtoCollection.iterator().next().getTitle(), technology.getTitle());
    Assertions.assertEquals(technologyDtoCollection.iterator().next().getDescription(),
        technology.getDescription());

  }
  /*
  @Test
  void shouldSaveTechnologys() {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("my title");
    technologyDto.setDescription("my description");
    technologyDto.setColor("my color");
    technologyDto.setPosition(0);
    technologyDto.setActive(true);
    //    not sure for this
    Mockito.when(technologyMapper.toDto(technologyRepository.save(technologyMapper.toEntity(technologyDto)))).thenReturn(Optional.of(technologyDto));
    technologyService.save(technologyDto);
    Mockito.verify(technologyRepository).save(technologyMapper.toEntity(technologyDto));
  }
   */

  @Test
  void shouldFindByIdTechnologies() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    List<Technology> technologyList = List.of(technology);
    Mockito.when(technologyRepository.findById(technology.getId())).thenReturn(Optional.of(technology));

    technologyService.findById(technology.getId());
    Mockito.verify(technologyRepository).findById(technology.getId());
  }

  @Test
  void shouldFindByTitleTechnologies() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    Mockito.when(technologyRepository.findByTitle(technology.getTitle())).thenReturn(Optional.of(technology));
    technologyService.findByTitle(technology.getTitle());

    Mockito.verify(technologyRepository).findByTitle(technology.getTitle());

  }

  @Test
  void shouldDeleteTechnology() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    List<Technology> technologyList = List.of(technology);
    Mockito.when(technologyRepository.findById(technology.getId())).thenReturn(Optional.of(technology));

    technologyService.deleteById(technology.getId());
    Mockito.verify(technologyRepository).deleteById(technology.getId());

  }
}