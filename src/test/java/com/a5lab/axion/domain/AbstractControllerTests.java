package com.a5lab.axion.domain;

import java.util.Locale;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.a5lab.axion.config.ApplicationTestBaseConfig;

@ApplicationTestBaseConfig
public abstract class AbstractControllerTests extends AbstractAnyTests {
  @Autowired
  protected MockMvc mockMvc;
}