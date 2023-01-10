package com.a5lab.tabr.config;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.View;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

@RequiredArgsConstructor
public class DecoratedThymeleafViewResolver extends ThymeleafViewResolver {

  private static final String SLASH = "/";

  private static final int DEFAULT_CACHE_SIZE = 256;
  private static final Map<String, String> cachedViews =
      new ConcurrentHashMap<>(DEFAULT_CACHE_SIZE);

  private final ApplicationContext applicationContext;

  private final ThymeleafProperties thymeleafProperties;

  @Override
  public View resolveViewName(String viewName, Locale locale) throws Exception {

    final String viewKey = getViewKey(viewName, locale);

    //Better to remove this logic to controllers
    final String alreadyProcessedView = cachedViews.get(viewKey);
    if (alreadyProcessedView != null) {
      return super.resolveViewName(alreadyProcessedView, locale);
    }

    if (StringUtils.endsWith(thymeleafProperties.getPrefix(), SLASH)) {
      viewName = StringUtils.removeStart(viewName, SLASH);
    }

    final String viewNameWithLocale =
        new StringBuilder().append(viewName).append("_").append(locale.getLanguage()).toString();


    if (applicationContext.getResource(new StringBuilder().append(thymeleafProperties.getPrefix())
            .append(viewNameWithLocale).append(thymeleafProperties.getSuffix()).toString())
        .exists()) {
      cachedViews.putIfAbsent(viewKey, viewNameWithLocale);
      return super.resolveViewName(viewNameWithLocale, locale);
    }

    cachedViews.putIfAbsent(viewKey, viewName);
    return super.resolveViewName(viewName, locale);
  }

  private String getViewKey(String viewName, Locale locale) {
    return viewName + '_' + locale.getLanguage();
  }

}
