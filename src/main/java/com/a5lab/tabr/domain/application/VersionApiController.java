package com.a5lab.tabr.domain.application;

import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/version")
@RequiredArgsConstructor
public class VersionApiController {

  @Value("${application.group_id}")
  private String groupId;

  @Value("${application.artifact_id}")
  private String artifactId;

  @Value("${application.version}")
  private String version;

  @Value("${application.name}")
  private String name;

  @Value("${application.description}")
  private String description;

  @GetMapping("/show")
  public Map<String, String> show() {
    Map<String, String> result = new HashMap<>();
    result.put("groupId", groupId);
    result.put("artifactId", artifactId);
    result.put("version", version);
    result.put("name", name);
    result.put("description", description);
    return result;
  }
}
