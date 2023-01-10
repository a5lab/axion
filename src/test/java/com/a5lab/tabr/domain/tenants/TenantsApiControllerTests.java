package com.a5lab.tabr.domain.tenants;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import com.a5lab.tabr.AbstractRepositoryTests;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

public class TenantsApiControllerTests  extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void index() throws Exception {
    List<Tenant> tenantList = new ArrayList<Tenant>();
    tenantList.add(new Tenant(0L, "title", "description"));
    Mockito.when(tenantService.findAll()).thenReturn(tenantList);

    // How to seed database for all tests
    String content = mockMvc.perform(get("/api/v1/tenants")
            .contentType(APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();
    Assertions.assertFalse(content.isBlank());
  }

}
