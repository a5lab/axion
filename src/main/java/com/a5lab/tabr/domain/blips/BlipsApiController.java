package com.a5lab.tabr.domain.blips;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/blips")
@RequiredArgsConstructor
public class BlipsApiController {

  private final BlipService blipService;

  @GetMapping("")
  public Collection<Blip> index() {
    return blipService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
