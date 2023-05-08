package com.a5lab.axion.domain.technology;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.tenant.Tenant;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

class TechnologyServiceTests extends AbstractServiceTests {
  private final TechnologyRepository technologyRepository = Mockito.mock(TechnologyRepository.class);

  private final TechnologyMapper technologyMapper = Mappers.getMapper(TechnologyMapper.class);

  private final TechnologyService technologyService = new TechnologyServiceImpl(technologyRepository, technologyMapper);

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
    Assertions.assertEquals(technologyDtoCollection.iterator().next().getDescription(), technology.getDescription());
  }

  @Test
  void shouldFindAllTechnologiesWithFilter() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    List<Technology> technologyList = List.of(technology);
    Page<Technology> page = new PageImpl<>(technologyList);
    Mockito.when(technologyRepository.findAll(ArgumentMatchers.<Specification<Technology>>any(), any(Pageable.class)))
        .thenReturn(page);

    TechnologyFilter technologyFilter = new TechnologyFilter();
    Pageable pageable = PageRequest.of(0, 10, Sort.by("title,asc"));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(technologyFilter, pageable);
    Assertions.assertEquals(1, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getId(), technology.getId());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getTitle(), technology.getTitle());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getDescription(), technology.getDescription());

    // Mockito.verify(technologyRepository).findAll(Specification.allOf((root, query, criteriaBuilder) -> null), pageable);
  }

  @Test
  void shouldFindByIdTechnology() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    Mockito.when(technologyRepository.findById(technology.getId())).thenReturn(Optional.of(technology));

    Optional<TechnologyDto> technologyDtoOptional = technologyService.findById(technology.getId());
    Assertions.assertTrue(technologyDtoOptional.isPresent());
    Assertions.assertEquals(technology.getId(), technologyDtoOptional.get().getId());
    Assertions.assertEquals(technology.getTitle(), technologyDtoOptional.get().getTitle());
    Assertions.assertEquals(technology.getWebsite(), technologyDtoOptional.get().getWebsite());
    Assertions.assertEquals(technology.getDescription(), technologyDtoOptional.get().getDescription());
    Assertions.assertEquals(technology.getMoved(), technologyDtoOptional.get().getMoved());

    Mockito.verify(technologyRepository).findById(technology.getId());
  }

  @Test
  void shouldFindByTitleTechnology() {
    final Technology technology = new Technology();
    technology.setId(10L);
    technology.setTitle("My technology");
    technology.setWebsite("My website");
    technology.setDescription("My technology description");
    technology.setMoved(0);
    technology.setActive(true);

    Mockito.when(technologyRepository.findByTitle(technology.getTitle())).thenReturn(Optional.of(technology));

    Optional<TechnologyDto> technologyDtoOptional = technologyService.findByTitle(technology.getTitle());
    Assertions.assertTrue(technologyDtoOptional.isPresent());
    Assertions.assertEquals(technology.getId(), technologyDtoOptional.get().getId());
    Assertions.assertEquals(technology.getTitle(), technologyDtoOptional.get().getTitle());
    Assertions.assertEquals(technology.getWebsite(), technologyDtoOptional.get().getWebsite());
    Assertions.assertEquals(technology.getDescription(), technologyDtoOptional.get().getDescription());
    Assertions.assertEquals(technology.getMoved(), technologyDtoOptional.get().getMoved());

    Mockito.verify(technologyRepository).findByTitle(technology.getTitle());
  }

  @Test
  void shouldSaveTechnology() {
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