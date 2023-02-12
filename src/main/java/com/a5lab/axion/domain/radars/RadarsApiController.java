package com.a5lab.axion.domain.radars;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/radars")
@RequiredArgsConstructor
public class RadarsApiController {

  private final RadarService radarService;

  @GetMapping("")
  public Collection<Radar> index() {
    return radarService.findAll();
  }
}
