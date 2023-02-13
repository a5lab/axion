package com.a5lab.axion.domain.technology_blip;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blips")
@RequiredArgsConstructor
public class TechnologyBlipApiController {

  private final TechnologyBlipService technologyBlipService;

  @GetMapping("")
  public Collection<TechnologyBlipDto> index() {
    return technologyBlipService.findAll();
  }
}
