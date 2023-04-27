package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.a5lab.axion.domain.radar_type.RadarType;
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
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final Wizard wizard = new Wizard(radarType);

    Mockito.doAnswer((i) -> null).when(wizardService).createRadarEnv(wizard);

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(wizard.getRadarType().getId()))
            .sessionAttr("wizard", wizard))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been created successfully."))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToCreateRadar() throws Exception {
    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldThrowExceptionToCreateRadar() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);

    final RadarType radarType1 = new RadarType();
    radarType1.setId(11L);
    radarType1.setCode(RadarType.CAPABILITY_RADAR);

    final Wizard wizard = new Wizard(radarType);

    final Wizard wizard1 = new Wizard(radarType1);

    Mockito.doThrow(Exception.class).when(wizardService).createRadarEnv(any());

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(wizard.getRadarType().getId()))
            .param("radarType.id", String.valueOf(wizard1.getRadarType().getId())))
        .andExpect(status().is3xxRedirection())
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Unable to create radar due to error."))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

}
