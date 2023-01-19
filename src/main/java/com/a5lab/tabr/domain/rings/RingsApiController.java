package com.a5lab.tabr.domain.rings;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rings")
@RequiredArgsConstructor
public class RingsApiController {

  private final RingService ringService;

  @GetMapping("")
  public Collection<Ring> index() {
    return ringService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
