package com.a5lab.axion.domain.wizard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeService;
import com.a5lab.axion.domain.radar.Radar;

import com.a5lab.axion.domain.tenant.TenantDto;
import com.a5lab.axion.utils.FlashMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.axion.domain.AbstractControllerTests;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(WizardController.class)
public class WizardControllerTests extends AbstractControllerTests {

  @MockBean
  private WizardService wizardService;

  @MockBean
  private RadarTypeService radarTypeService;

  @Test
  public void shouldAddRadar() throws Exception {
    MvcResult result = mockMvc.perform(get("/wizard/add"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("radar_type_id"));
  }

  @Test
  public void shouldCreateRadar() throws Exception {

    // TODO: mock wizardService.createRadarEnv(wizard);
    // final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");
    // Mockito.when(tenantService.findById(tenantDto.getId())).thenReturn(Optional.of(tenantDto));

    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radars title");
    radar.setDescription("My radars description");
    radar.setPrimary(true);
    radar.setActive(true);

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radar.getRadarType().getId()))
            .param("title", radar.getTitle())
            .param("description", radar.getDescription())
            .param("primary", String.valueOf(radar.isPrimary()))
            .param("active", String.valueOf(radar.isActive()))
            .sessionAttr("radarDto", radar))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been created successfully."))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToCreateRadar() throws Exception {

    // TODO: mock wizardService.createRadarEnv(wizard); to throw exception
    // final TenantDto tenantDto = new TenantDto(10L, "My title", "My description");
    // Mockito.when(tenantService.findById(tenantDto.getId())).thenReturn(Optional.of(tenantDto));

    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final Radar radar = new Radar();
    radar.setRadarType(radarType);
    radar.setTitle("My radars title");
    radar.setDescription("My radars description");
    radar.setPrimary(true);
    radar.setActive(true);

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radar.getRadarType().getId()))
            .param("title", radar.getTitle())
            .param("description", radar.getDescription())
            .param("primary", String.valueOf(radar.isPrimary()))
            .param("active", String.valueOf(radar.isActive()))
            .sessionAttr("radarDto", radar))
        .andExpect(status().isOk())
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Unable to create radar due to error."))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }
}
