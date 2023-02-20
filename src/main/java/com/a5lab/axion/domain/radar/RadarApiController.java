package com.a5lab.axion.domain.radar;

import jakarta.validation.Valid;
import java.util.Collection;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/v1/radars")
@RequiredArgsConstructor
public class RadarApiController {

  private final RadarService radarService;

  @GetMapping("")
  public List<Radar> index(@Valid RadarFilter radarFilter,
                           @RequestParam(defaultValue = "${application.paging.page}") int page,
                           @RequestParam(defaultValue = "${application.paging.size}") int size,
                           @RequestParam(defaultValue = "title,asc") String[] sort) {
    Sort.Direction direction = sort[1].equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    Sort.Order order = new Sort.Order(direction, sort[0]);
    Page<Radar> radarPage =
        radarService.findAll(radarFilter, PageRequest.of(page - 1, size, Sort.by(order)));
    return radarPage.getContent();
  }
}
