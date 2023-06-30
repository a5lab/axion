package com.a5lab.axion.domain.technology_blip;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.ring.RingService;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.segment.SegmentService;
import com.a5lab.axion.domain.technology.TechnologyDto;
import com.a5lab.axion.domain.technology.TechnologyService;
import com.a5lab.axion.utils.FlashMessages;

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
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(3L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRadarTitle(radarDto.getTitle());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    technologyBlipDto.setSegmentTitle(segmentDto.getTitle());

    Page<TechnologyBlipDto> page = new PageImpl<>(List.of(technologyBlipDto));
    Mockito.when(technologyBlipService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/technology_blips"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/index"))
        .andExpect(model().attributeExists("technologyBlipDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(technologyBlipDto.getRadarTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getTechnologyTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getSegmentTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getRingTitle()));

    Mockito.verify(technologyBlipService).findAll(any(), any());
  }

  @Test
  public void shouldShowTechnologyBlip() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(3L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRadarTitle(radarDto.getTitle());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    technologyBlipDto.setSegmentTitle(segmentDto.getTitle());

    Mockito.when(technologyBlipService.findById(any())).thenReturn(Optional.of(technologyBlipDto));

    String url = String.format("/settings/technology_blips/show/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(technologyBlipDto.getRadarTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getTechnologyTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getSegmentTitle()));
    Assertions.assertTrue(content.contains(technologyBlipDto.getRingTitle()));

    Mockito.verify(technologyBlipService).findById(technologyBlipDto.getId());
  }

  @Test
  public void shouldRedirectShowTechnologyBlip() throws Exception {
    Mockito.when(technologyBlipService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/technology_blips/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid technology blip id."))
        .andReturn();

    Mockito.verify(technologyBlipService).findById(any());
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
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(3L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRadarTitle(radarDto.getTitle());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    technologyBlipDto.setSegmentTitle(segmentDto.getTitle());

    Mockito.when(technologyBlipService.save(any(TechnologyBlipDto.class))).thenReturn(technologyBlipDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(technologyBlipDto.getRadarId()))
            .param("technologyId", String.valueOf(technologyBlipDto.getTechnologyId()))
            .param("segmentId", String.valueOf(technologyBlipDto.getSegmentId()))
            .param("ringId", String.valueOf(technologyBlipDto.getRingId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology blip has been created successfully."))
        .andReturn();

    Assertions.assertEquals(technologyBlipDto.getRadarId(), radarDto.getId());
    Assertions.assertEquals(technologyBlipDto.getRadarTitle(), radarDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getRingId(), ringDto.getId());
    Assertions.assertEquals(technologyBlipDto.getRingTitle(), ringDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getSegmentId(), segmentDto.getId());
    Assertions.assertEquals(technologyBlipDto.getSegmentTitle(), segmentDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getTechnologyId(), technologyDto.getId());
    Assertions.assertEquals(technologyBlipDto.getTechnologyTitle(), technologyDto.getTitle());

    Mockito.verify(technologyBlipService).save(any(TechnologyBlipDto.class));
  }

  @Test
  public void shouldFailToCreateTechnologyBlip() throws Exception {
    MvcResult result = mockMvc.perform(post("/settings/technology_blips/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/add"))
        .andReturn();
  }

  @Test
  public void shouldRedirectCreateTechnologyBlip() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(3L);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.doThrow(DataIntegrityViolationException.class).when(technologyBlipService)
        .save(any(TechnologyBlipDto.class));

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(technologyBlipDto.getRadarId()))
            .param("technologyId", String.valueOf(technologyBlipDto.getTechnologyId()))
            .param("segmentId", String.valueOf(technologyBlipDto.getSegmentId()))
            .param("ringId", String.valueOf(technologyBlipDto.getRingId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.ERROR, "Unable to save technology blip due to data integrity violation."))
        .andReturn();

    Mockito.verify(technologyBlipService).save(any(TechnologyBlipDto.class));
  }

  @Test
  public void shouldEditTechnologyBlip() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setTitle("Technology radars 1");
    radarTypeDto.setCode("technology_radar_1");
    radarTypeDto.setDescription("Technology radars");

    // Create a radar
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(2L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My new test Radar");
    radarDto.setDescription("My awesome description");
    radarDto.setPrimary(false);
    radarDto.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(4L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(5L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(6L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.when(technologyBlipService.findById(any())).thenReturn(Optional.of(technologyBlipDto));

    String url = String.format("/settings/technology_blips/edit/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/edit"))
        .andReturn();

    Mockito.verify(technologyBlipService).findById(technologyBlipDto.getId());
  }

  @Test
  public void shouldRedirectEditTechnologyBlip() throws Exception {
    Mockito.when(technologyBlipService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/technology_blips/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid technology blip id."))
        .andReturn();

    Mockito.verify(technologyBlipService).findById(any());
  }

  @Test
  public void shouldUpdateTechnologyBlip() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setTitle("Technology radars 1");
    radarTypeDto.setCode("technology_radar_1");
    radarTypeDto.setDescription("Technology radars");

    // Create a radar
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(2L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My new test Radar");
    radarDto.setDescription("My awesome description");
    radarDto.setPrimary(false);
    radarDto.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(3L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRadarTitle(radarDto.getTitle());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setRingTitle(ringDto.getTitle());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setTechnologyTitle(technologyDto.getTitle());
    technologyBlipDto.setSegmentId(segmentDto.getId());
    technologyBlipDto.setSegmentTitle(segmentDto.getTitle());

    Mockito.when(technologyBlipService.save(any(TechnologyBlipDto.class))).thenReturn(technologyBlipDto);

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(technologyBlipDto.getRadarId()))
            .param("technologyId", String.valueOf(technologyBlipDto.getTechnologyId()))
            .param("segmentId", String.valueOf(technologyBlipDto.getSegmentId()))
            .param("ringId", String.valueOf(technologyBlipDto.getRingId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology blip has been updated successfully."))
        .andReturn();

    Assertions.assertEquals(technologyBlipDto.getRadarId(), radarDto.getId());
    Assertions.assertEquals(technologyBlipDto.getRadarTitle(), radarDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getRingId(), ringDto.getId());
    Assertions.assertEquals(technologyBlipDto.getRingTitle(), ringDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getSegmentId(), segmentDto.getId());
    Assertions.assertEquals(technologyBlipDto.getSegmentTitle(), segmentDto.getTitle());
    Assertions.assertEquals(technologyBlipDto.getTechnologyId(), technologyDto.getId());
    Assertions.assertEquals(technologyBlipDto.getTechnologyTitle(), technologyDto.getTitle());

    Mockito.verify(technologyBlipService).save(any(TechnologyBlipDto.class));
  }

  @Test
  public void shouldFailToUpdateTechnologyBlip() throws Exception {
    MvcResult result = mockMvc.perform(post("/settings/technology_blips/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technology_blips/edit"))
        .andReturn();
  }

  @Test
  public void shouldRedirectUpdateTechnologyBlip() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);

    final RingDto ringDto = new RingDto();
    ringDto.setId(2L);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(3L);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.doThrow(DataIntegrityViolationException.class).when(technologyBlipService)
        .save(any(TechnologyBlipDto.class));

    MvcResult result = mockMvc.perform(post("/settings/technology_blips/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(technologyBlipDto.getRadarId()))
            .param("technologyId", String.valueOf(technologyBlipDto.getTechnologyId()))
            .param("segmentId", String.valueOf(technologyBlipDto.getSegmentId()))
            .param("ringId", String.valueOf(technologyBlipDto.getRingId()))
            .sessionAttr("technologyBlipDto", technologyBlipDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.ERROR, "Unable to save technology blip due to data integrity violation."))
        .andReturn();

    Mockito.verify(technologyBlipService).save(any((TechnologyBlipDto.class)));
  }

  @Test
  public void shouldDeleteTechnologyBlip() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment title");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);
    segmentDto.setActive(true);

    final RingDto ringDto = new RingDto();
    ringDto.setId(3L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My ring title");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(1);
    ringDto.setColor("#fbdb84");
    ringDto.setActive(true);

    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(4L);
    technologyDto.setTitle("My technology");
    technologyDto.setWebsite("My website");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(5L);
    technologyBlipDto.setRadarId(radarDto.getId());
    technologyBlipDto.setRingId(ringDto.getId());
    technologyBlipDto.setTechnologyId(technologyDto.getId());
    technologyBlipDto.setSegmentId(segmentDto.getId());

    Mockito.doAnswer((i) -> null).when(technologyBlipService).deleteById(any());

    String url = String.format("/settings/technology_blips/delete/%d", technologyBlipDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technology_blips"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology blip has been deleted successfully."))
        .andReturn();

    Mockito.verify(technologyBlipService).deleteById(technologyBlipDto.getId());
  }
}
