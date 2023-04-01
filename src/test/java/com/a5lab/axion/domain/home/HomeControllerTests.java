package com.a5lab.axion.domain.home;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar_type.RadarTypeService;


@WebMvcTest(HomeController.class)
public class HomeControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;
  @MockBean
  private RadarTypeService radarTypeService;
  @MockBean
  private MessageSource messageSource;

  @Test
  public void shouldGetHome() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("my title");
    radarDto.setDescription("my description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.findByPrimaryAndActive(true, true))
        .thenReturn(List.of(radarDto));

    MvcResult result = mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attributeExists("radarDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(radarDto.getId().toString()));
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));
  }

  @Test
  public void shouldGetHomeWithNoPrimaryRadar() throws Exception {

    Mockito.when(radarService.findByPrimary(true)).thenReturn(Optional.empty());

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attributeDoesNotExist("radarDto"));
  }
}
