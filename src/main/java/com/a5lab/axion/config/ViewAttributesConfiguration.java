package com.a5lab.axion.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ViewAttributesConfiguration implements WebMvcConfigurer {

  @Value("${application.keys.google_analytics}")
  private String googleAnalytics;

  private static final String VIEW_SERVLET_PATH_ATTRIBUTE = "servletPath";

  private static final String VIEW_GOOGLE_ANALYTICS_ATTRIBUTE = "googleAnalytics";

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {
      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler, ModelAndView modelAndView) {
        if (modelAndView != null && modelAndView.hasView() && modelAndView.isReference()) {
          // Add a servlet path
          Optional.ofNullable(request.getServletPath()).ifPresent(it ->
              modelAndView.addObject(VIEW_SERVLET_PATH_ATTRIBUTE, it)
          );

          // Add a Google Analytics
          if (!googleAnalytics.isEmpty() && !googleAnalytics.trim().isBlank()) {
            modelAndView.addObject(VIEW_GOOGLE_ANALYTICS_ATTRIBUTE, googleAnalytics);
          }
        }
      }
    });
  }
}
