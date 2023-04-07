package com.a5lab.axion.domain.technology_blip;


import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarType;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;


import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.a5lab.axion.domain.AbstractControllerTests;

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

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

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

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

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
  public void shouldRedirectShowTechnologyBlip() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technology_blips/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();
  }

  @Test
  public void shouldAddTechnologyBlip() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technology_blips/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/add"))
        .andExpect(model().attributeExists("technologyBlipDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("radar"));
    Assertions.assertTrue(content.contains("Technology"));
    Assertions.assertTrue(content.contains("Segment"));
    Assertions.assertTrue(content.contains("ring"));
  }

  @Test
  public void shouldCreateTechnologyBlip() throws Exception {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(radar);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radar);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar.id", String.valueOf(technologyBlipDto.getRadar().getId()))
            .param("technology.id", String.valueOf(technologyBlipDto.getTechnology().getId()))
            .param("segment.id", String.valueOf(technologyBlipDto.getSegment().getId()))
            .param("ring.id", String.valueOf(technologyBlipDto.getRing().getId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  /*

  @Test
  public void shouldFailToCreateTenant() throws Exception {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto(10L, null, "My segment title","My segment description",1,true);

    final RingDto ringDto = new RingDto(10L, null, "My ring title", "My ring description", 1, true);

    final TechnologyDto technologyDto = new TechnologyDto(10L, "My technology", "My website", "My radar description", 1, true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto(10L, radar, technologyDto, segmentDto, ringDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar", technologyBlipDto.getRadar().getTitle())
            .param("technology", technologyBlipDto.getTechnology().getTitle())
            .param("segment", technologyBlipDto.getSegment().getTitle())
            .param("ring", technologyBlipDto.getRing().getTitle())
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }
*/

  @Test
  public void shouldEditTechnologyBlip() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");

    // Create a radar
    final Radar radar = new Radar();
    radar.setId(2L);
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(radar);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radar);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    Mockito.when(technologyBlipService.findById(technologyBlipDto.getId())).thenReturn(Optional.of(technologyBlipDto));

    String url = String.format("/settings/technology_blips/edit/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();

  }

  @Test
  public void shouldRedirectEditTechnologyBlip() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technology_blips/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();
  }

  @Test
  public void shouldUpdateTechnologyBlip() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);
    radarType.setTitle("Technology radars 1");
    radarType.setCode("technology_radar_1");
    radarType.setDescription("Technology radars");

    // Create a radar
    final Radar radar = new Radar();
    radar.setId(2L);
    radar.setRadarType(radarType);
    radar.setTitle("My new test Radar");
    radar.setDescription("My awesome description");
    radar.setPrimary(false);
    radar.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(radar);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radar);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar.id", String.valueOf(technologyBlipDto.getRadar().getId()))
            .param("technology.id", String.valueOf(technologyBlipDto.getTechnology().getId()))
            .param("segment.id", String.valueOf(technologyBlipDto.getSegment().getId()))
            .param("ring.id", String.valueOf(technologyBlipDto.getRing().getId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }


  @Test
  public void shouldFailToUpdateTechnologyBlip() throws Exception {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar", technologyBlipDto.getRadar().getTitle())
            .param("technology", technologyBlipDto.getTechnology().getTitle())
            .param("segment", technologyBlipDto.getSegment().getTitle())
            .param("ring", technologyBlipDto.getRing().getTitle())
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldDeleteTechnologyBlip() throws Exception {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setTitle("My radar");
    radar.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setRadar(null);
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    String url = String.format("/settings/technology_blips/delete/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andReturn();
  }
}
