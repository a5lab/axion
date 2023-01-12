package com.a5lab.tabr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ApplicationConfiguration {

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    PropertySourcesPlaceholderConfigurer propsConfigurer =
        new PropertySourcesPlaceholderConfigurer();
    propsConfigurer.setLocation(new ClassPathResource("git.properties"));
    propsConfigurer.setIgnoreResourceNotFound(true);
    propsConfigurer.setIgnoreUnresolvablePlaceholders(true);
    return propsConfigurer;
  }

}
