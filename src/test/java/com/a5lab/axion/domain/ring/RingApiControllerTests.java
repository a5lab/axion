package com.a5lab.axion.domain.ring;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarDto;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@WebMvcTest(RingApiController.class)
public class RingApiControllerTests extends AbstractControllerTests {
  @MockBean
  private RingService ringService;

  /* TODO: fix it
  @Test
  public void shouldGetRings() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(12L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final RingDto ringDto = new RingDto();
    ringDto.setId(10L);
    ringDto.setRadarId(radarDto.getId());
    ringDto.setTitle("My title");
    ringDto.setDescription("My description");
    Page<RingDto> ringDtoPage = new PageImpl<>(Arrays.asList(ringDto));
    Mockito.when(ringService.findAll(any(), any())).thenReturn(ringDtoPage);

    mockMvc.perform(get("/api/v1/rings").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(ringDtoPage.getContent().size())))
        .andExpect(jsonPath("$[0].id", equalTo(ringDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].radar_id", equalTo(ringDto.getRadarId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(ringDto.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(ringDto.getDescription())))
        .andExpect(jsonPath("$[0].color", equalTo(ringDto.getColor())))
        .andExpect(jsonPath("$[0].active", equalTo(ringDto.isActive())));
  }
   */
}
