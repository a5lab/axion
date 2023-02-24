package axion.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts/notifications")
@RequiredArgsConstructor
public class NotificationController {

  @GetMapping("")
  public String index() {
    return "accounts/notifications/index";
  }

}
