package com.a5lab.tabr.domain.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Commit {

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
}
