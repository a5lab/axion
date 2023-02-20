package com.a5lab.axion.domain.technology;

import jakarta.validation.Valid;
import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/technologies")
@RequiredArgsConstructor
public class TechnologyApiController {

  private final TechnologyService technologyService;

  @GetMapping("")
  public Collection<TechnologyDto> index(@Valid TechnologyFilter technologyFilter,
                                         @RequestParam(defaultValue = "${application.paging.page}") int page,
                                         @RequestParam(defaultValue = "${application.paging.size}") int size,
                                         @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);
    Page<TechnologyDto> technologyDtoPage =
        technologyService.findAll(technologyFilter, PageRequest.of(page - 1, size, Sort.by(order)));
    return technologyDtoPage.getContent();
  }
}
