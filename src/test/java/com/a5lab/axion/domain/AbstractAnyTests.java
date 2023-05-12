package com.a5lab.axion.domain;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
public abstract class AbstractAnyTests {
  static Locale defaultLocale = Locale.getDefault();

  @BeforeAll
  public static void setDefaultLocale() {
    Locale.setDefault(Locale.US);
  }

  @AfterAll
  public static void restoreLocale() {
    Locale.setDefault(defaultLocale);
  }
}
