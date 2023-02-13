package com.a5lab.axion.domain.technology_blip;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blips")
@RequiredArgsConstructor
public class BlipApiController {

  private final BlipService blipService;

  @GetMapping("")
  public Collection<BlipDto> index() {
    return blipService.findAll();
  }
}
