package com.a5lab.tabr.domain.application;


import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/commit")
@RequiredArgsConstructor
public class CommitApiController {

  @Value("${git.branch}")
  private String branch;

  @Value("${git.build.time}")
  private String buildTime;

  @Value("${git.build.version}")
  private String buildVersion;

  @Value("${git.commit.id.abbrev}")
  private String commitIdAbbrev;

  @Value("${git.commit.id.full}")
  private String commitIdFull;

  @Value("${git.commit.message.full}")
  private String commitMessageFull;

  @Value("${git.commit.message.short}")
  private String commitMessageShort;

  @Value("${git.commit.time}")
  private String commitTime;

  @Value("${git.dirty}")
  private String dirty;

  @Value("${git.tags}")
  private String tags;

  @GetMapping("/show")
  public Map<String, String> show() {
    Map<String, String> result = new HashMap<>();
    result.put("Git branch", branch);
    result.put("Git build time", buildTime);
    result.put("Git build version", buildVersion);
    result.put("Git commit id abbrev", commitIdAbbrev);
    result.put("Git commit id full", commitIdFull);
    result.put("Git commit message full", commitMessageFull);
    result.put("Git commit message short", commitMessageShort);
    result.put("Git commit time", commitTime);
    result.put("Git dirty", dirty);
    result.put("Git tags", tags);
    return result;
  }
}
