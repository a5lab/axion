package com.a5lab.tabr.config;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class ControllerConfig {

  /**
   * The public name of a hero that is common knowledge.
   *
   * @param binder the binder
   */
  @InitBinder
  void initBinder(final WebDataBinder binder) {
    binder.registerCustomEditor(String.class,
        new StringTrimmerEditor(true));
  }

}
