package com.a5lab.tabr.domain.radars;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.a5lab.tabr.AbstractControllerTests;

@WebMvcTest(RadarsApiController.class)
public class RadarsApiControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetRadars() throws Exception {
    final RadarDto radarDto = new RadarDto(10L, "my title", "my description", true);
    List<RadarDto> radarDtoList = Arrays.asList(radarDto);
    Mockito.when(radarService.findAll()).thenReturn(radarDtoList);

    mockMvc.perform(get("/api/v1/radars").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(radarDtoList.size())))
        .andExpect(jsonPath("$[0].id", equalTo(radarDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(radarDto.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(radarDto.getDescription())))
        .andExpect(jsonPath("$[0].primary", equalTo(radarDto.isPrimary())));
  }
}
