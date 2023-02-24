package com.a5lab.axion.domain.exception;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Profile("dev")
public class ExceptionController implements ErrorController {

  @RequestMapping("/error")
  public ModelAndView handleError(HttpServletRequest httpRequest) {

    ModelAndView modelAndView = new ModelAndView("exception/index");
    int statusCode = (Integer) httpRequest.getAttribute("jakarta.servlet.error.status_code");
    modelAndView.addObject("errorCode", statusCode);
    switch (statusCode) {
      case 400: {
        modelAndView.addObject("errorMsg", "Bad request");
        break;
      }
      case 401: {
        modelAndView.addObject("errorMsg", "Unauthorized");
        break;
      }
      case 404: {
        modelAndView.addObject("errorMsg", "Resource not found");
        break;
      }
      case 500: {
        modelAndView.addObject("errorMsg", "Internal server error");
        break;
      }
      default: {
        modelAndView.addObject("errorMsg", "Unclassified");
        break;
      }
    }
    return modelAndView;
  }
}