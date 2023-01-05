package com.a5lab.tabr.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationController {

  @Value("${application.keys.google_analytics}")
  public String googleAnalytics;
}
