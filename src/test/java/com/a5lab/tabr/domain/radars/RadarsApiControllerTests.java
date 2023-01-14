package com.a5lab.tabr.domain.tenants;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;

public class TenantsApiControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void index() throws Exception {
    final Tenant tenant = new Tenant(10L, "my title", "my description");
    List<Tenant> tenantList = Arrays.asList(tenant);
    Mockito.when(tenantService.findAll()).thenReturn(tenantList);

    mockMvc.perform(get("/api/v1/tenants").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(tenantList.size())))
        .andExpect(jsonPath("$[0].id", equalTo(tenant.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(tenant.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(tenant.getDescription())));

  }
}
