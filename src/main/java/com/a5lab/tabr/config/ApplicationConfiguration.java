package com.a5lab.tabr.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class ApplicationConfiguration {
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:/messages/application",
        "classpath:/messages/tenant"
    );
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
