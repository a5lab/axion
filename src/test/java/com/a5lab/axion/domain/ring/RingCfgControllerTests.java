package com.a5lab.axion.domain.ring;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;

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
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(RingCfgController.class)
public class RingCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private RingService ringService;

  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetRings() throws Exception {
    // Create radar
    final RadarDto radarDto = new RadarDto();
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    // Create ring for radar
    final RingDto ringDto = new RingDto();
    ringDto.setRadar(radarDto);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    List<RingDto> ringList = List.of(ringDto);
    Page<RingDto> page = new PageImpl<>(ringList);
    Mockito.when(ringService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/rings"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/index"))
        .andExpect(model().attributeExists("ringDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(ringDto.getTitle()));
    Assertions.assertTrue(content.contains(ringDto.getDescription()));

    Mockito.verify(ringService).findAll(any(), any());
  }

  @Test
  public void shouldShowRing() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    Mockito.when(ringService.findById(any())).thenReturn(Optional.of(ringDto));

    String url = String.format("/settings/rings/show/%d", ringDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(ringDto.getTitle()));
    Assertions.assertTrue(content.contains(ringDto.getDescription()));

    Mockito.verify(ringService).findById(ringDto.getId());
  }

  @Test
  public void shouldRedirectShowRing() throws Exception {
    Mockito.when(ringService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/rings/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid ring id."))
        .andReturn();

    Mockito.verify(ringService).findById(any());
  }

  @Test
  public void shouldAddRing() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/rings/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/add"))
        .andExpect(model().attributeExists("ringDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("description"));
  }

  @Test
  public void shouldFailToCreateRingOnLowerCaseTitle() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/rings/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("description", ringDto.getDescription())
            .param("title", ringDto.getTitle())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("should be uppercase"));
  }

  @Test
  public void shouldCreateRing() throws Exception {
    // Create a radar type
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

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radarDto);
    ringDto.setTitle("TRIAL");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    Mockito.when(ringService.save(any())).thenReturn(ringDto);

    MvcResult result = mockMvc.perform(post("/settings/rings/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar.id", String.valueOf(ringDto.getRadar().getId()))
            .param("title", ringDto.getTitle())
            .param("description", ringDto.getDescription())
            .param("color", ringDto.getColor())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The ring has been created successfully."))
        .andReturn();

    Mockito.verify(ringService).save(any());
  }

  @Test
  public void shouldFailToCreateRingOnBlankDescription() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/rings/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", ringDto.getTitle())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldEditRing() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    Mockito.when(ringService.findById(any())).thenReturn(Optional.of(ringDto));

    String url = String.format("/settings/rings/edit/%d", ringDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(ringDto.getTitle()));
    Assertions.assertTrue(content.contains(ringDto.getDescription()));

    Mockito.verify(ringService).findById(ringDto.getId());
  }


  @Test
  public void shouldRedirectEditRing() throws Exception {
    Mockito.when(ringService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/rings/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid ring id."))
        .andReturn();

    Mockito.verify(ringService).findById(any());
  }

  @Test
  public void shouldUpdateRing() throws Exception {
    // Create a radar type
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

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(radarDto);
    ringDto.setTitle("TRIAL");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    Mockito.when(ringService.save(any())).thenReturn(ringDto);

    MvcResult result = mockMvc.perform(post("/settings/rings/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radar.id", String.valueOf(ringDto.getRadar().getId()))
            .param("title", ringDto.getTitle())
            .param("description", ringDto.getDescription())
            .param("color", ringDto.getColor())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The ring has been updated successfully."))
        .andReturn();

    Mockito.verify(ringService).save(any());
  }

  @Test
  public void shouldFailToUpdateRing() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/rings/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", ringDto.getTitle())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/rings/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldDeleteRing() throws Exception {
    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadar(null);
    ringDto.setTitle("My ring");
    ringDto.setDescription("My ring description");
    ringDto.setColor("#fbdb84");
    ringDto.setPosition(0);
    ringDto.setActive(true);

    Mockito.doAnswer((i) -> null).when(ringService).deleteById(any());

    String url = String.format("/settings/rings/delete/%d", ringDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The ring has been deleted successfully."))
        .andReturn();

    Mockito.verify(ringService).deleteById(ringDto.getId());
  }
}
