package com.a5lab.tabr.config;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@RequiredArgsConstructor
public class DecoratedThymeleafViewResolver extends ThymeleafViewResolver {

  private static final String SLASH = "/";
  private static final Set<String> cachedViews = new HashSet<>();

  private final ApplicationContext applicationContext;

  private final ThymeleafProperties thymeleafProperties;

  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {

    if (StringUtils.endsWith(thymeleafProperties.getPrefix(), SLASH)) {
      viewName = StringUtils.removeStart(viewName, SLASH);
    }

    final String viewNameWithLocale =
        new StringBuilder().append(viewName).append("_").append(locale.getLanguage()).toString();

    // Also we should cache not found views
    // It's better to move this logic to controller
    if (cachedViews.contains(viewNameWithLocale) || applicationContext.getResource(
            new StringBuilder().append(thymeleafProperties.getPrefix()).append(viewNameWithLocale)
                .append(thymeleafProperties.getSuffix()).toString())
        .exists()) {
      cachedViews.add(viewNameWithLocale);
      return super.resolveViewName(viewNameWithLocale, locale);
    }

    return super.resolveViewName(viewName, locale);
  }
}
