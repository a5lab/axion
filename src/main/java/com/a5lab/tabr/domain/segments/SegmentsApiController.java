package com.a5lab.tabr.domain.segments;

import java.util.Collection;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/segments")
@RequiredArgsConstructor
public class SegmentsApiController {

  private final SegmentService segmentService;

  @GetMapping("")
  public Collection<Segment> index() {
    return segmentService.findAll();
  }

  // todo: implement based on https://spring.io/guides/tutorials/rest/

}
