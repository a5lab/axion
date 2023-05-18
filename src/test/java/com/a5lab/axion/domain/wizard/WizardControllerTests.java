package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeService;

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
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);

    final WizardDto wizardDto = new WizardDto(radarTypeDto);

    Mockito.doAnswer((i) -> null).when(wizardService).createRadarEnv(any());

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(wizardDto.getRadarType().getId()))
            .sessionAttr("wizard", wizardDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been created successfully."))
        .andReturn();

    Mockito.verify(wizardService).createRadarEnv(any());
  }

  @Test
  public void shouldFailToCreateRadar() throws Exception {
    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("wizard/add"))
        .andReturn();
  }

  @Test
  public void shouldThrowExceptionToCreateRadar() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);

    final WizardDto wizardDto = new WizardDto(radarTypeDto);

    Mockito.doThrow(Exception.class).when(wizardService).createRadarEnv(any());

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(wizardDto.getRadarType().getId())))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Unable to create radar due to error."))
        .andReturn();

    Mockito.verify(wizardService).createRadarEnv(any());
  }
}
