package com.a5lab.axion.domain.radars;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.a5lab.axion.domain.radar_types.RadarType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.a5lab.axion.domain.AbstractControllerTests;

@WebMvcTest(RadarsApiController.class)
public class RadarsApiControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;


  @Test
  public void shouldGetRadars() throws Exception {
    final RadarType radarType = new RadarType();
    final Radar radar = new Radar(10L, radarType, "my title", "my description", true, false, null);
    List<Radar> radarList = Arrays.asList(radar);
    Mockito.when(radarService.findAll()).thenReturn(radarList);

    mockMvc.perform(get("/api/v1/radars").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(radarList.size())))
        .andExpect(jsonPath("$[0].id", equalTo(radar.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(radar.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(radar.getDescription())))
        .andExpect(jsonPath("$[0].primary", equalTo(radar.isPrimary())));
  }
}
