package com.a5lab.axion.domain.exception;


import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.a5lab.axion.utils.ProfileConstants;

@Controller
@ConfigurationProperties
public class ExceptionController implements ErrorController {

  @Value("${spring.profiles.active}")
  private String profile;

  @RequestMapping("/error")
  public ModelAndView handleError(HttpServletRequest httpRequest, Exception e) {

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

    if (profile.contains(ProfileConstants.DEV_PROFILE)) {
      String backTrace = (String) httpRequest.getAttribute("jakarta.servlet.error.message");
      modelAndView.addObject("backTrace", backTrace);
      // modelAndView.addObject("backTrace", e.getStackTrace().toString());
    }
    return modelAndView;
  }
}