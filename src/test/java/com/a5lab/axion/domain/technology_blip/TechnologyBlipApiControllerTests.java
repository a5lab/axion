package com.a5lab.axion.domain.technology_blip;

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
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.TechnologyDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@WebMvcTest(TechnologyBlipApiController.class)
public class TechnologyBlipApiControllerTests extends AbstractControllerTests {
  @MockBean
  private TechnologyBlipService technologyBlipService;

  @Test
  public void shouldGetTechnologyBlips() throws Exception {
    final Radar radar = new Radar();
    final SegmentDto segmentDto = new SegmentDto();
    final RingDto ringDto = new RingDto();
    final TechnologyDto technologyDto = new TechnologyDto();
    final TechnologyBlipDto technologyBlipDto = new TechnologyBlipDto();
    technologyBlipDto.setId(10L);
    technologyBlipDto.setRadar(radar);
    technologyBlipDto.setRing(ringDto);
    technologyBlipDto.setTechnology(technologyDto);
    technologyBlipDto.setSegment(segmentDto);

    Page<TechnologyBlipDto> technologyBlipDtoPage = new PageImpl<>(Arrays.asList(technologyBlipDto));
    Mockito.when(technologyBlipService.findAll(any(), any())).thenReturn(technologyBlipDtoPage);

    mockMvc.perform(get("/api/v1/technology_blips").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(technologyBlipDtoPage.getContent().size())))
        .andExpect(jsonPath("$[0].id", equalTo(technologyBlipDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].radar.id", equalTo(technologyBlipDto.getRadar().getId()), Long.class))
        .andExpect(jsonPath("$[0].ring.id", equalTo(technologyBlipDto.getRing().getId()), Long.class))
        .andExpect(jsonPath("$[0].technology.id", equalTo(technologyBlipDto.getTechnology().getId()), Long.class))
        .andExpect(jsonPath("$[0].segment.id", equalTo(technologyBlipDto.getSegment().getId()), Long.class));
  }
}