package axion.domain.application;

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
@ConfigurationProperties(prefix = "application")
public class Version {

  @Value("${group_id}")
  private String groupId;

  @Value("${artifact_id}")
  private String artifactId;

  @Value("${version}")
  private String version;

  @Value("${name}")
  private String name;

  @Value("${description}")
  private String description;
}
