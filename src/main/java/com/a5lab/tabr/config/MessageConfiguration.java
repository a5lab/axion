package com.a5lab.tabr.config;

import java.util.LinkedHashMap;
import java.util.Locale;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@Configuration
@RequiredArgsConstructor
public class MessageConfiguration implements WebMvcConfigurer {

  private final ApplicationContext applicationContext;

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

  @Bean
  public LocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
    localeResolver.setDefaultLocale(Locale.US);
    return localeResolver;
  }

  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor
        = new LocaleChangeInterceptor();
    localeChangeInterceptor.setParamName("language");
    return localeChangeInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(localeChangeInterceptor());
  }

  @Bean
  ThymeleafViewResolver thymeleafViewResolver(ThymeleafProperties properties,
                                              SpringTemplateEngine templateEngine) {
    final DecoratedThymeleafViewResolver resolver =
        new DecoratedThymeleafViewResolver(applicationContext, properties);
    resolver.setTemplateEngine(templateEngine);
    resolver.setCharacterEncoding(properties.getEncoding().name());
    resolver.setContentType(this.appendCharset(properties.getServlet().getContentType(),
        resolver.getCharacterEncoding()));
    resolver.setProducePartialOutputWhileProcessing(
        properties.getServlet().isProducePartialOutputWhileProcessing());
    resolver.setExcludedViewNames(properties.getExcludedViewNames());
    resolver.setViewNames(properties.getViewNames());
    resolver.setOrder(2147483642);
    resolver.setCache(properties.isCache());
    return resolver;
  }

  private String appendCharset(MimeType type, String charset) {
    if (type.getCharset() != null) {
      return type.toString();
    } else {
      LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
      parameters.put("charset", charset);
      parameters.putAll(type.getParameters());
      return new MimeType(type, parameters).toString();
    }
  }

}
