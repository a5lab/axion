package com.a5lab.tabr;

import com.a5lab.tabr.domain.tenants.TenantsApiController;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TenantsApiController.class)
public abstract class AbstractControllerTests {
  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ApplicationContext context;
}