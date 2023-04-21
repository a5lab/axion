package com.a5lab.axion.domain.radar;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.a5lab.axion.domain.radar_type.RadarTypeDto;
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
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    final RadarDto radarDto = new RadarDto(10L, radarTypeDto, "My title", "My description", true, false, null, null, null);
    Page<RadarDto> radarDtoPage = new PageImpl<>(Arrays.asList(radarDto));
    Mockito.when(radarService.findAll(any(), any())).thenReturn(radarDtoPage);

    mockMvc.perform(get("/api/v1/radars").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(radarDtoPage.getContent().size())))
        .andExpect(jsonPath("$[0].id", equalTo(radarDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(radarDto.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(radarDto.getDescription())))
        .andExpect(jsonPath("$[0].primary", equalTo(radarDto.isPrimary())));
  }
}
