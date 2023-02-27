package com.a5lab.axion.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProvider implements ApplicationContextAware {
  private static ApplicationContext context;

  public static ApplicationContext getApplicationContext() {
    return context;
  }

  @Override
  @Autowired
  public void setApplicationContext(ApplicationContext ctx) throws BeansException {
    context = ctx;
  }

  public static <T> T getBean(Class<T> beanClass) {
    return context.getBean(beanClass);
  }
}
