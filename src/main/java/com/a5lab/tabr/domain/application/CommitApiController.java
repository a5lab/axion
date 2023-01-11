package com.a5lab.tabr.domain.application;


import java.util.HashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/commit")
@RequiredArgsConstructor
public class CommitApiController {

  // @Value("${git.commit.message.short}")
  private String commitMessage;

  // @Value("${git.branch}")
  private String branch;

  // @Value("${git.commit.id}")
  private String commitId;

  @RequestMapping("/show")
  public Map<String, String> show() {
    Map<String, String> result = new HashMap<>();
    result.put("Commit message", commitMessage);
    result.put("Commit branch", branch);
    result.put("Commit id", commitId);
    return result;
  }
}
