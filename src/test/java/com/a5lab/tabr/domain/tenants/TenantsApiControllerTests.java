package com.a5lab.tabr.domain.tenants;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.a5lab.tabr.AbstractControllerTests;

@WebMvcTest(TenantsApiController.class)
public class TenantsApiControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    List<TenantDto> tenantDtoList = Arrays.asList(tenantDto);
    Mockito.when(tenantService.findAll()).thenReturn(tenantDtoList);

    mockMvc.perform(get("/api/v1/tenants").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(tenantDtoList.size())))
        .andExpect(jsonPath("$[0].id", equalTo(tenantDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(tenantDto.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(tenantDto.getDescription())));

  }
}
