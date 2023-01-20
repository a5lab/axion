package com.a5lab.tabr.domain.application;


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
  public Commit show() {
    Commit commit = new Commit();
    commit.setBranch(this.branch);
    commit.setBuildTime(this.buildTime);
    commit.setBuildVersion(this.buildVersion);
    commit.setCommitIdAbbrev(this.commitIdAbbrev);
    commit.setCommitIdFull(this.commitIdFull);
    commit.setCommitMessageFull(this.commitMessageFull);
    commit.setCommitMessageShort(this.commitMessageShort);
    commit.setCommitTime(this.commitTime);
    commit.setDirty(this.dirty);
    commit.setTags(this.tags);
    return commit;
  }
}
