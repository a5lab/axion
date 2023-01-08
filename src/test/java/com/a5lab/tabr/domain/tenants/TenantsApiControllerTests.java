package com.a5lab.tabr.domain.tenants;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(TenantsApiController.class)
public class TenantsApiControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TenantService tenantService;

  @Test
  public void index() throws Exception {
    String result = mockMvc.perform(get("/api/v1/tenants")
            .contentType(APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
  }
}
