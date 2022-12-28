package com.a5lab.tabr.config;

import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CommonViewAttributesConfiguration implements WebMvcConfigurer {

  private static final String VIEW_SERVLET_PATH_ATTRIBUTE = "servletPath";

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new HandlerInterceptor() {
      @Override
      public void postHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler, ModelAndView modelAndView) {
        if (modelAndView != null && modelAndView.hasView() && modelAndView.isReference()) {
          Optional.ofNullable(request.getServletPath()).ifPresent(it ->
              modelAndView.addObject(VIEW_SERVLET_PATH_ATTRIBUTE, it)
          );
        }
      }
    });
  }
}
