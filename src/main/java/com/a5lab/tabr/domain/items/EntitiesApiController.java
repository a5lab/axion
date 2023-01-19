package com.a5lab.tabr.domain.entities;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntitiesApiController {

  private final EntityService entityService;

  @GetMapping("")
  public Collection<Entity> index() {
    return entityService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
