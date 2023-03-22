package com.a5lab.axion.domain.radar;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import com.a5lab.axion.domain.radar_type.RadarType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.a5lab.axion.domain.AbstractControllerTests;

@WebMvcTest(RadarApiController.class)
public class RadarApiControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;


  @Test
  public void shouldGetRadars() throws Exception {
    final RadarType radarType = new RadarType();
    final Radar radar = new Radar(10L, radarType, "my title", "my description", true, false, null, null);
    Page<Radar> radarPage = new PageImpl<>(Arrays.asList(radar));
    Mockito.when(radarService.findAll(any(), any())).thenReturn(radarPage);

    mockMvc.perform(get("/api/v1/radars").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(radarPage.getContent().size())))
        .andExpect(jsonPath("$[0].id", equalTo(radar.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(radar.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(radar.getDescription())))
        .andExpect(jsonPath("$[0].primary", equalTo(radar.isPrimary())));
  }
}
