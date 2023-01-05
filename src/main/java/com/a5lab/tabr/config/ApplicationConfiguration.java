package com.a5lab.tabr.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public interface ApplicationConfiguration {
  @Bean
  public MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource
        = new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames(
        "classpath:/messages/api_error_messages",
        "classpath:/messages/api_response_messages"
    );
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }
}
