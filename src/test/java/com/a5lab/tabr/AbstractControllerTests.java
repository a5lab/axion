package com.a5lab.tabr;

import com.a5lab.tabr.domain.tenants.TenantsApiController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TenantsApiController.class)
public abstract class AbstractControllerTests {
  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ApplicationContext context;
}