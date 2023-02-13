package com.a5lab.axion.domain.technology;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/entries")
@RequiredArgsConstructor
public class TechnologyApiController {

  private final TechnologyService entityService;

  @GetMapping("")
  public Collection<TechnologyDto> index() {
    return entityService.findAll();
  }
}
