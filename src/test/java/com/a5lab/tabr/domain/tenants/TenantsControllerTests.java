package com.a5lab.tabr.domain.tenants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

public class TenantsControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void index() throws Exception {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantList = Arrays.asList(tenant);
    Mockito.when(tenantService.findAll()).thenReturn(tenantList);

    MvcResult result = mockMvc.perform(get("/settings/tenants"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();
    System.out.println(content);
  }
}
