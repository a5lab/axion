package com.a5lab.tabr.domain.application;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/version")
@RequiredArgsConstructor
public class VersionApiController {


  @RequestMapping("/show")
  public Map<String, String> show() {
    Map<String, String> result = new HashMap<>();
    result.put("version", "x.y.z");
    return result;
  }
}
