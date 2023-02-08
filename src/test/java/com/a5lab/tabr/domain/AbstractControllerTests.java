package com.a5lab.tabr.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.a5lab.tabr.config.ApplicationTestBaseConfig;

@ApplicationTestBaseConfig
public abstract class AbstractControllerTests {
  @Autowired
  protected MockMvc mockMvc;

}