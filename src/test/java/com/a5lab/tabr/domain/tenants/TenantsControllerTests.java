package com.a5lab.tabr.domain.tenants;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(TenantsController.class)
public class TenantsControllerTests extends AbstractControllerTests {
  @MockBean
  private TenantService tenantService;

  @Test
  public void shouldGetIndex() throws Exception {
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
  public void shouldGetShow() throws Exception {
    /*
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    List<TenantDto> tenantList = Arrays.asList(tenantDto);
    Page<TenantDto> page = new PageImpl<>(tenantList);
    Mockito.when(tenantService.findAll(Pageable.ofSize(100))).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/tenants/show/10"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));
     */
  }

  @Test
  public void shouldGetAdd() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/tenants/add"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("Title"));
    Assertions.assertTrue(content.contains("Description"));
  }

  @Test
  public void shouldPostCreate() throws Exception {
    // TODO
  }

  @Test
  public void shouldGetEdit() throws Exception {
    /*
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    List<TenantDto> tenantList = Arrays.asList(tenantDto);
    Page<TenantDto> page = new PageImpl<>(tenantList);
    Mockito.when(tenantService.findAll(Pageable.ofSize(100))).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/tenants/edit/10"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("Title"));
    Assertions.assertTrue(content.contains("Description"));
    */
  }

  @Test
  public void shouldPostUpdate() throws Exception {
    // TODO
  }

  public void shouldGetDelete() throws Exception {
    /*
    final TenantDto tenantDto = new TenantDto(10L, "my title", "my description");
    List<TenantDto> tenantList = Arrays.asList(tenantDto);
    Page<TenantDto> page = new PageImpl<>(tenantList);
    Mockito.when(tenantService.findAll(Pageable.ofSize(100))).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/tenants/delete/10"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(tenantDto.getTitle()));
    Assertions.assertTrue(content.contains(tenantDto.getDescription()));
     */
  }
}
