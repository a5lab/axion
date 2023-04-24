package com.a5lab.axion.domain;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import com.a5lab.axion.config.ApplicationTestBaseConfig;
import com.a5lab.axion.config.JpaAuditingConfiguration;

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
