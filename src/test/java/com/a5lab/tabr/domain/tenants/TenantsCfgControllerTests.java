package com.a5lab.tabr.domain.tenants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(TenantsCfgController.class)
public class TenantsCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    List<TenantDto> tenantList = Arrays.asList(tenantDto);
    Page<TenantDto> page = new PageImpl<>(tenantList);
    Mockito.when(tenantService.findAll(Pageable.ofSize(100))).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/tenants"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));
  }

  @Test
  public void shouldShowTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    Mockito.when(tenantService.findById(tenantDto.getId())).thenReturn(Optional.of(tenantDto));

    String url = String.format("/settings/tenants/show/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));
  }

  @Test
  public void shouldRedirectShowTenant() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/tenants/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();
  }

  @Test
  public void shouldAddTentant() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/tenants/add"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("Title"));
    Assertions.assertTrue(content.contains("Description"));
  }

  @Test
  public void shouldCreateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .param("description", tenantDto.getDescription())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToCreateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldEditTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    Mockito.when(tenantService.findById(tenantDto.getId())).thenReturn(Optional.of(tenantDto));

    String url = String.format("/settings/tenants/edit/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));
  }

  @Test
  public void shouldRedirectEditTenant() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/tenants/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();
  }

  @Test
  public void shouldUpdateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .param("description", tenantDto.getDescription())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToUpdateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldDeleteTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");

    String url = String.format("/settings/tenants/delete/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();
  }
}