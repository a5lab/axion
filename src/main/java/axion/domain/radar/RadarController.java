package axion.domain.radar;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/radars")
@RequiredArgsConstructor
public class RadarController {

  private final RadarService radarService;

  @GetMapping("")
  public ModelAndView index() {
    ModelAndView modelAndView = new ModelAndView("radars/index");
    modelAndView.addObject("radars", radarService.findAll(null, Pageable.ofSize(100)).getContent());
    return modelAndView;
  }

}
