package com.a5lab.tabr.config;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

public class ApplicationViewResolver implements ViewResolver {

  @Autowired
  private HttpServletRequest httpServletRequest;

  private ViewResolver applicationViewResolver;

  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {
    return applicationViewResolver.resolveViewName(viewName + "_de", locale);
  }
}
