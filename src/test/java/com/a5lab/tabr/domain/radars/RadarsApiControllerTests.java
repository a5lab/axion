package com.a5lab.tabr.domain.radars;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(RadarsApiController.class)
public class RadarsApiControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetIndex() throws Exception {
    final Radar radar = new Radar(10L, "my title", "my description", true);
    List<Radar> radarList = Arrays.asList(radar);
    Mockito.when(radarService.findAll()).thenReturn(radarList);

    mockMvc.perform(get("/api/v1/radars").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(radarList.size())))
        .andExpect(jsonPath("$[0].id", equalTo(radar.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(radar.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(radar.getDescription())))
        .andExpect(jsonPath("$[0].prime", equalTo(radar.isPrime())));
  }
}
