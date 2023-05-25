package com.a5lab.axion.domain.home;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;


@WebMvcTest(HomeController.class)
public class HomeControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetHome() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.findByPrimaryAndActive(any(boolean.class), any(boolean.class)))
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
    Mockito.when(radarService.findByPrimaryAndActive(any(boolean.class), any(boolean.class)))
        .thenReturn(new ArrayList<RadarDto>());

    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"));
  }
}
