package com.a5lab.axion.domain.wizard;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeService;
import com.a5lab.axion.utils.FlashMessages;


@WebMvcTest(WizardController.class)
public class WizardControllerTests extends AbstractControllerTests {

  @MockBean
  private WizardService wizardService;

  @MockBean
  private RadarTypeService radarTypeService;

  @Test
  public void shouldAddRadar() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setTitle("Radar type title");
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeDto.setDescription("Radar type description");

    Mockito.when(radarTypeService.findByCode(radarTypeDto.getCode())).thenReturn(Optional.of(radarTypeDto));

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
    radarTypeDto.setTitle("Radar type title");
    radarTypeDto.setCode(RadarType.TECHNOLOGY_RADAR);
    radarTypeDto.setDescription("Radar type description");

    Mockito.when(radarTypeService.findById(radarTypeDto.getId())).thenReturn(Optional.of(radarTypeDto));

    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(radarTypeDto.getId());
    wizardDto.setRadarTypeCode(radarTypeDto.getCode());
    wizardDto.setRadarTypeTitle(radarTypeDto.getTitle());

    Mockito.doAnswer((i) -> null).when(wizardService).createRadarEnv(any());

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarTypeId", String.valueOf(wizardDto.getRadarTypeId()))
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
    final WizardDto wizardDto = new WizardDto();
    wizardDto.setRadarTypeId(10L);
    wizardDto.setRadarTypeCode(RadarType.TECHNOLOGY_RADAR);

    Mockito.doThrow(new UnsupportedOperationException("Not supported yet.")).when(wizardService).createRadarEnv(any());

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarTypeId", String.valueOf(wizardDto.getRadarTypeId())))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/home"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.ERROR, "Unable to create radar due to error: not supported yet."))
        .andReturn();

    Mockito.verify(wizardService).createRadarEnv(any());
  }
}
