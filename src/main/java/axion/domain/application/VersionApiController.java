package axion.domain.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/version")
@RequiredArgsConstructor
@EnableConfigurationProperties(Version.class)
public class VersionApiController {

  private final Version version;

  @GetMapping("/show")
  public Version show() {
    return version;
  }
}
