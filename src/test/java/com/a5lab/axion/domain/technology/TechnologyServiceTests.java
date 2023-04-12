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
    Assertions.assertEquals(technologyDtoCollection.iterator().next().getDescription(),technology.getDescription());
  }


  @Test
  void shouldFindByIdTechnologies() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);
    Mockito.when(technologyRepository.findById(technology.getId())).thenReturn(Optional.of(technology));

    Optional<TechnologyDto> technologyDtoOptional = technologyService.findById(technology.getId());
    Assertions.assertEquals(technology.getId(), technologyDtoOptional.get().getId());
    Assertions.assertEquals(technology.getTitle(), technologyDtoOptional.get().getTitle());
    Assertions.assertEquals(technology.getWebsite(), technologyDtoOptional.get().getWebsite());
    Assertions.assertEquals(technology.getDescription(), technologyDtoOptional.get().getDescription());
    Assertions.assertEquals(technology.getMoved(), technologyDtoOptional.get().getMoved());

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

    Optional<Technology> technologyOptional = technologyService.findByTitle(technology.getTitle());
    Assertions.assertEquals(technology.getId(), technologyOptional.get().getId());
    Assertions.assertEquals(technology.getTitle(), technologyOptional.get().getTitle());
    Assertions.assertEquals(technology.getWebsite(), technologyOptional.get().getWebsite());
    Assertions.assertEquals(technology.getDescription(), technologyOptional.get().getDescription());
    Assertions.assertEquals(technology.getMoved(), technologyOptional.get().getMoved());

    Mockito.verify(technologyRepository).findByTitle(technology.getTitle());
  }

  @Test
  void shouldSaveTechnologies() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);
    Mockito.when(technologyRepository.save(any())).thenReturn(technology);

    TechnologyDto technologyDto = technologyService.save(technologyMapper.toDto(technology));
    Assertions.assertEquals(technology.getId(), technologyDto.getId());
    Assertions.assertEquals(technology.getTitle(), technologyDto.getTitle());
    Assertions.assertEquals(technology.getWebsite(), technologyDto.getWebsite());
    Assertions.assertEquals(technology.getDescription(), technologyDto.getDescription());
    Assertions.assertEquals(technology.getMoved(), technologyDto.getMoved());

    Mockito.verify(technologyRepository).save(any());
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
    Mockito.doAnswer((i) -> null).when(technologyRepository).deleteById(technology.getId());

    technologyService.deleteById(technology.getId());
    Mockito.verify(technologyRepository).deleteById(technology.getId());
  }
}