package com.a5lab.tabr.domain.entries;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/entities")
@RequiredArgsConstructor
public class EntriesApiController {

  private final EntryService entityService;

  @GetMapping("")
  public Collection<Entry> index() {
    return entityService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
