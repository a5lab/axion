package com.a5lab.tabr.domain.application;

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
  public Version show() {
    Version version = new Version();
    version.setGroupId(this.groupId);
    version.setArtifactId(this.artifactId);
    version.setName(this.name);
    version.setDescription(this.description);
    return version;
  }
}
