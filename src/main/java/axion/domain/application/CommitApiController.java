package axion.domain.application;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/application/commit")
@RequiredArgsConstructor
@EnableConfigurationProperties(Commit.class)
public class CommitApiController {
  private final Commit commit;

  @GetMapping("/show")
  public Commit show() {
    return commit;
  }
}
