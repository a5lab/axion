package com.a5lab.axion.domain.technology;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.a5lab.axion.domain.AbstractServiceTests;

class TechnologyServiceRepositoryTests extends AbstractServiceTests {
  @Autowired
  private TechnologyRepository technologyRepository;
  @Autowired
  private TechnologyService technologyService;

  @Test
  @Transactional
  void shouldFindAllTechnologiesWithNullFilter() {
    List<Technology> technologyList = List.of(
        new Technology(null, "My title", "My website", "My description", 0, true),
        new Technology(null, "My new title", "My new website", "My new description", 0, true));
    for (Technology technology : technologyList) {
      technologyRepository.save(technology);
    }

    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(null, pageable);
    Assertions.assertEquals(10, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(2, technologyDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllTechnologiesWithBlankTitleFilter() {
    List<Technology> technologyList = List.of(
        new Technology(null, "My title", "My website",  "My description", 0, true),
        new Technology(null, "My new title", "My new website",  "My new description", 0, true));
    for (Technology technology : technologyList) {
      technologyRepository.save(technology);
    }

    TechnologyFilter technologyFilter = new TechnologyFilter();
    technologyFilter.setTitle("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(technologyFilter, pageable);
    Assertions.assertEquals(10, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(2, technologyDtoPage.getNumberOfElements());
  }

  @Test
  @Transactional
  void shouldFindAllTechnologiesWithTitleFilter() {
    List<Technology> technologyList = List.of(
        new Technology(null,  "My title",  "My website", "My description", 0, true),
        new Technology(null, "My new title",  "My new website", "My new description", 0, true));
    for (Technology technology : technologyList) {
      technologyRepository.save(technology);
    }

    TechnologyFilter technologyFilter = new TechnologyFilter();
    technologyFilter.setTitle(technologyList.iterator().next().getTitle());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(technologyFilter, pageable);
    Assertions.assertEquals(10, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(1, technologyDtoPage.getNumberOfElements());
    Assertions.assertNotNull(technologyDtoPage.iterator().next().getId());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getTitle(),
        technologyList.iterator().next().getTitle());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getDescription(),
        technologyList.iterator().next().getDescription());
  }

  @Test
  @Transactional
  void shouldFindAllTechnologiesWithBlankWebsiteFilter() {
    List<Technology> technologyList = List.of(
        new Technology(null,  "My first title",  "My first website", "My description", 0, true),
        new Technology(null, "My second title",  "My second website", "My new description", 0, true));
    for (Technology technology : technologyList) {
      technologyRepository.save(technology);
    }

    TechnologyFilter technologyFilter = new TechnologyFilter();
    technologyFilter.setWebsite("");
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(technologyFilter, pageable);
    Assertions.assertEquals(10, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(2, technologyDtoPage.getNumberOfElements());
    Assertions.assertNotNull(technologyDtoPage.iterator().next().getId());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getWebsite(),
        technologyList.iterator().next().getWebsite());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getDescription(),
        technologyList.iterator().next().getDescription());
  }

  @Test
  @Transactional
  void shouldFindAllTechnologiesWithWebsiteFilter() {
    List<Technology> technologyList = List.of(
        new Technology(null,  "My title",  "My first website", "My description", 0, true),
        new Technology(null, "My new title",  "My second website", "My new description", 0, true));
    for (Technology technology : technologyList) {
      technologyRepository.save(technology);
    }

    TechnologyFilter technologyFilter = new TechnologyFilter();
    technologyFilter.setWebsite(technologyList.iterator().next().getWebsite());
    Pageable pageable = PageRequest.of(0, 10, Sort.by(new Sort.Order(Sort.Direction.ASC, "title")));
    Page<TechnologyDto> technologyDtoPage = technologyService.findAll(technologyFilter, pageable);
    Assertions.assertEquals(10, technologyDtoPage.getSize());
    Assertions.assertEquals(0, technologyDtoPage.getNumber());
    Assertions.assertEquals(1, technologyDtoPage.getTotalPages());
    Assertions.assertEquals(1, technologyDtoPage.getNumberOfElements());
    Assertions.assertNotNull(technologyDtoPage.iterator().next().getId());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getTitle(),
        technologyList.iterator().next().getTitle());
    Assertions.assertEquals(technologyDtoPage.iterator().next().getDescription(),
        technologyList.iterator().next().getDescription());
  }
}
