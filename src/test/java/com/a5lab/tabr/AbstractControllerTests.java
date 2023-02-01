package com.a5lab.tabr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@ApplicationTestBaseConfig
public abstract class AbstractControllerTests {
  @Autowired
  protected MockMvc mockMvc;

}