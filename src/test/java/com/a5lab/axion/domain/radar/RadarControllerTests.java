package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.FlashMessages;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeService;

@WebMvcTest(RadarController.class)
public class RadarControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @MockBean
  private RadarTypeService radarTypeService;

  @Test
  public void shouldGetRadars() throws Exception {
    //Create radarType
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setDescription("My Description");
    radarTypeDto.setTitle("My title");
    radarTypeDto.setCode("My code");

    //Create radar for radarType
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<RadarDto> radarDtoList = List.of(radarDto);
    Page<RadarDto> page = new PageImpl<>(radarDtoList);
    Mockito.when(radarService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/radars"))
        .andExpect(status().isOk())
        .andExpect(view().name("radars/index"))
        .andExpect(model().attributeExists("radarDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findAll(any(), any());
  }

  @Test
  public void shouldShowRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.findById(any())).thenReturn(Optional.of(radarDto));

    String url = String.format("/radars/show/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("radars/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findById(radarDto.getId());
  }

  @Test
  public void shouldRedirectShowRadar() throws Exception {
    Mockito.when(radarService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/radars/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid radar id."))
        .andReturn();

    Mockito.verify(radarService).findById(any());
  }
}
