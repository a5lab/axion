package com.a5lab.tabr.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.tabr.AbstractControllerTests;
import com.a5lab.tabr.domain.radars.RadarDto;
import com.a5lab.tabr.domain.radars.RadarService;

@WebMvcTest(HomeController.class)
public class HomeControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetHome() throws Exception {
    final RadarDto dto =
        RadarDto.builder().id(1L).title("my title").description("my description").primary(true)
            .build();

    Mockito.when(radarService.findByPrimary(true)).thenReturn(Optional.of(dto));

    MvcResult result = mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("home/index"))
        .andExpect(model().attributeExists("radarDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(dto.getId().toString()));
    Assertions.assertTrue(content.contains(dto.getTitle()));
    Assertions.assertTrue(content.contains(dto.getDescription()));
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
