package com.a5lab.axion.domain.segment;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.segment.SegmentCfgController;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.tenant.TenantDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SegmentCfgController.class)
public class SegmentCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private SegmentService segmentService;
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetSegments() throws Exception {

    final Radar radar = new Radar();
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setRadar(radar);
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(0);
    segmentDto.setActive(true);

    Page<SegmentDto> page = new PageImpl<>(List.of(segmentDto));
    Mockito.when(segmentService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/segments"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/index"))
        .andExpect(model().attributeExists("segmentDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(segmentDto.getTitle()));
    Assertions.assertTrue(content.contains(segmentDto.getDescription()));


  }

  @Test
  public void shouldShowSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto(10L, null, "my title", "my description", 1, true);
    Mockito.when(segmentService.findById(segmentDto.getId())).thenReturn(Optional.of(segmentDto));

    String url = String.format("/settings/segments/show/%d", segmentDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(segmentDto.getTitle()));
    Assertions.assertTrue(content.contains(segmentDto.getDescription()));
  }

  @Test
  public void shouldRedirectShowSegment() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/segments/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andReturn();
  }

  @Test
  public void shouldAddSegment() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/segments/add"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("description"));
  }
  /*
  @Test
  public void shouldCreateSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto(10L, null, "my title", "my description", 1, true);

    MvcResult result = mockMvc.perform(post("/settings/tenants/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", segmentDto.getTitle())
            .param("description", segmentDto.getDescription())
            .sessionAttr("segmentDto", segmentDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }
  */
  @Test
  public void shouldFailToCreateSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto(10L, null, "my title", "my description", 1 , true);

    MvcResult result = mockMvc.perform(post("/settings/segments/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", segmentDto.getTitle())
            .sessionAttr("segmentDto", segmentDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }
  /*
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