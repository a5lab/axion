package com.a5lab.axion.domain.technology_blip;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TechnologyBlipCfgController.class)

public class TechnologyBlipCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private TechnologyBlipService technologyBlipService;
  @MockBean
  private RadarService radarService;
  @MockBean
  private TechnologyService technologyService;
  @MockBean
  private SegmentService segmentService;
  @MockBean
  private RingService ringService;

  @Test
  public void shouldGetTechnologyBlips() throws Exception {

    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto(10L, null, "My segment title","My segment description",1,true);

    final RingDto ringDto = new RingDto(10L, null, "My ring title", "My ring description", 0, true);

    final TechnologyDto technologyDto = new TechnologyDto(10L, "My technology", "My website", "My radar description", 1, true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto(10L, radar, technologyDto, segmentDto, ringDto);


    Page<TechnologyBlipDto> page = new PageImpl<>(List.of(technologyBlipDto));
    Mockito.when(technologyBlipService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/technology_blips"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/index"))
        .andExpect(model().attributeExists("technologyBlipDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(technologyBlipDto.getRadar().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getTechnology().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getSegment().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getRing().getTitle()));
  }


  @Test
  public void shouldShowTechnologyBlip() throws Exception {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto(10L, null, "My segment title","My segment description",1,true);

    final RingDto ringDto = new RingDto(10L, null, "My ring title", "My ring description", 1, true);

    final TechnologyDto technologyDto = new TechnologyDto(10L, "My technology", "My website", "My radar description", 1, true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto(10L, radar, technologyDto, segmentDto, ringDto);

    Mockito.when(technologyBlipService.findById(technologyBlipDto.getId())).thenReturn(Optional.of(technologyBlipDto));

    String url = String.format("/settings/technology_blips/show/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(technologyBlipDto.getRadar().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getTechnology().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getSegment().getTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getRing().getTitle()));
  }

  @Test
  public void shouldRedirectShowTechnology_blip() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technology_blips/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();
  }
/*
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
