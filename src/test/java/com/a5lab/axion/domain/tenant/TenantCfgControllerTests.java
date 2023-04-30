package com.a5lab.axion.domain.tenant;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.utils.FlashMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.axion.domain.AbstractControllerTests;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TenantCfgController.class)
public class TenantCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void shouldGetTenants() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");
    Page<TenantDto> page = new PageImpl<>(List.of(tenantDto));
    Mockito.when(tenantService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/tenants"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/index"))
        .andExpect(model().attributeExists("tenantDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));

    Mockito.verify(tenantService).findAll(any(), any());
  }

  @Test
  public void shouldShowTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");
    Mockito.when(tenantService.findById(any())).thenReturn(Optional.of(tenantDto));

    String url = String.format("/settings/tenants/show/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));

    Mockito.verify(tenantService).findById(tenantDto.getId());
  }

  @Test
  public void shouldRedirectShowTenant() throws Exception {
    Mockito.when(tenantService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/tenants/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid tenant id."))
        .andReturn();

    Mockito.verify(tenantService).findById(any());
  }

  @Test
  public void shouldAddTenant() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/tenants/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/add"))
        .andExpect(model().attributeExists("tenantDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("description"));
  }

  @Test
  public void shouldCreateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    Mockito.when(tenantService.save(any())).thenReturn(tenantDto);

    MvcResult result = mockMvc.perform(post("/settings/tenants/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .param("description", tenantDto.getDescription())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The tenant has been created successfully."))
        .andReturn();

    Mockito.verify(tenantService).save(any());
  }

  @Test
  public void shouldFailToCreateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldEditTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    Mockito.when(tenantService.findById(any())).thenReturn(Optional.of(tenantDto));

    String url = String.format("/settings/tenants/edit/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));

    Mockito.verify(tenantService).findById(tenantDto.getId());
  }

  @Test
  public void shouldRedirectEditTenant() throws Exception {
    Mockito.when(tenantService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/tenants/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid tenant id."))
        .andReturn();

    Mockito.verify(tenantService).findById(any());
  }

  @Test
  public void shouldUpdateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    Mockito.when(tenantService.save(any())).thenReturn(tenantDto);

    MvcResult result = mockMvc.perform(post("/settings/tenants/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .param("description", tenantDto.getDescription())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The tenant has been updated successfully."))
        .andReturn();

    Mockito.verify(tenantService).save(any());
  }

  @Test
  public void shouldFailToUpdateTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    MvcResult result = mockMvc.perform(post("/settings/tenants/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", tenantDto.getTitle())
            .sessionAttr("tenantDto", tenantDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/tenants/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldDeleteTenant() throws Exception {
    final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");

    Mockito.doAnswer((i) -> null).when(tenantService).deleteById(any());

    String url = String.format("/settings/tenants/delete/%d", tenantDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The tenant has been deleted successfully."))
        .andReturn();

    Mockito.verify(tenantService).deleteById(tenantDto.getId());
  }
}
