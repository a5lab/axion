package com.a5lab.tabr.domain.application;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties
public class Version {

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
}
