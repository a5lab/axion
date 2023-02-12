package com.a5lab.axion.domain.segments;

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
  public Collection<SegmentDto> index() {
    return segmentService.findAll();
  }
}
