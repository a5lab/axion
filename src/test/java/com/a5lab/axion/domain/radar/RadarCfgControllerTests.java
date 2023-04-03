package com.a5lab.axion.domain.radar;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RadarCfgController.class)
public class RadarCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @MockBean
  private RadarTypeService radarTypeService;

  @Test
  public void shouldGetRadars() throws Exception {
    //Create radarType
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setDescription("My Description");
    radarType.setTitle("My title");
    radarType.setCode("My code");

    //Create radar for radarType
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(radarType);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<RadarDto> radarDtoList = List.of(radarDto);
    Page<RadarDto> page = new PageImpl<>(radarDtoList);
    Mockito.when(radarService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/radars"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/index"))
        .andExpect(model().attributeExists("radarDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));
  }

  /*
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
 */
}
