package com.a5lab.axion.domain.segment;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.FlashMessages;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar.RadarDto;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;

@WebMvcTest(SegmentCfgController.class)
public class SegmentCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private SegmentService segmentService;
  @MockBean
  private RadarService radarService;

  @Test
  public void shouldGetSegments() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(1L);
    radarDto.setTitle("My radar");
    radarDto.setDescription("My radar description");

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(2L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Page<SegmentDto> page = new PageImpl<>(List.of(segmentDto));
    Mockito.when(segmentService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/segments"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/index"))
        .andExpect(model().attributeExists("segmentDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(segmentDto.getTitle()));
    Assertions.assertTrue(content.contains(segmentDto.getDescription()));

    Mockito.verify(segmentService).findAll(any(), any());
  }

  @Test
  public void shouldShowSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Mockito.when(segmentService.findById(any())).thenReturn(Optional.of(segmentDto));

    String url = String.format("/settings/segments/show/%d", segmentDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(segmentDto.getTitle()));
    Assertions.assertTrue(content.contains(segmentDto.getDescription()));

    Mockito.verify(segmentService).findById(segmentDto.getId());
  }

  @Test
  public void shouldRedirectShowSegment() throws Exception {
    Mockito.when(segmentService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/segments/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid segment id."))
        .andReturn();

    Mockito.verify(segmentService).findById(any());
  }

  @Test
  public void shouldAddSegment() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/segments/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/add"))
        .andExpect(model().attributeExists("segmentDto"))
        .andReturn();
  }

  @Test
  public void shouldCreateSegment() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setTitle("Technology radars 1");
    radarTypeDto.setCode("technology_radar_1");
    radarTypeDto.setDescription("Technology radars");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(2L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My test Radar");
    radarDto.setDescription("My description");
    radarDto.setPrimary(false);
    radarDto.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setRadarTitle(radarDto.getTitle());
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Mockito.when(segmentService.save(any())).thenReturn(segmentDto);

    MvcResult result = mockMvc.perform(post("/settings/segments/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(segmentDto.getRadarId()))
            .param("title", segmentDto.getTitle())
            .param("description", segmentDto.getDescription())
            .sessionAttr("segmentDto", segmentDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The segment has been created successfully."))
        .andReturn();

    Assertions.assertEquals(segmentDto.getRadarId(), radarDto.getId());
    Assertions.assertEquals(segmentDto.getRadarTitle(), radarDto.getTitle());

    Mockito.verify(segmentService).save(any());
  }

  @Test
  public void shouldFailToCreateSegmentDueToActiveRadar() throws Exception {
    List<ModelError> modelErrorList =
        List.of(new ModelError("unable_to_save_active_radar", "can't be saved for active radar", null));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(segmentService)
        .save(any());

    MvcResult result = mockMvc.perform(post("/settings/segments/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("can't be saved for active radar"));

    Mockito.verify(segmentService).save(any());
  }

  @Test
  public void shouldFailToCreateSegmentDueToEmptyTitle() throws Exception {
    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(segmentService)
        .save(any(SegmentDto.class));

    MvcResult result = mockMvc.perform(post("/settings/segments/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(segmentService).save(any(SegmentDto.class));
  }

  @Test
  public void shouldEditSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Mockito.when(segmentService.findById(any())).thenReturn(Optional.of(segmentDto));

    String url = String.format("/settings/segments/edit/%d", segmentDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(segmentDto.getTitle()));
    Assertions.assertTrue(content.contains(segmentDto.getDescription()));

    Mockito.verify(segmentService).findById(segmentDto.getId());
  }

  @Test
  public void shouldRedirectEditSegment() throws Exception {
    Mockito.when(segmentService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/segments/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid segment id."))
        .andReturn();

    Mockito.verify(segmentService).findById(any());
  }

  @Test
  public void shouldUpdateSegment() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(1L);
    radarTypeDto.setTitle("Technology radars 1");
    radarTypeDto.setCode("technology_radar_1");
    radarTypeDto.setDescription("Technology radars");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(2L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My test Radar");
    radarDto.setDescription("My description");
    radarDto.setPrimary(false);
    radarDto.setActive(false);

    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(3L);
    segmentDto.setRadarId(radarDto.getId());
    segmentDto.setRadarTitle(radarDto.getTitle());
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Mockito.when(segmentService.save(any())).thenReturn(segmentDto);

    MvcResult result = mockMvc.perform(post("/settings/segments/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarId", String.valueOf(segmentDto.getRadarId()))
            .param("title", segmentDto.getTitle())
            .param("description", segmentDto.getDescription())
            .sessionAttr("segmentDto", segmentDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The segment has been updated successfully."))
        .andReturn();

    Assertions.assertEquals(segmentDto.getRadarId(), radarDto.getId());
    Assertions.assertEquals(segmentDto.getRadarTitle(), radarDto.getTitle());

    Mockito.verify(segmentService).save(any());
  }

  @Test
  public void shouldFailToUpdateSegmentDueToEmptyTitle() throws Exception {
    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(segmentService)
        .save(any(SegmentDto.class));

    MvcResult result = mockMvc.perform(post("/settings/segments/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(segmentService).save(any(SegmentDto.class));
  }

  @Test
  public void shouldFailToUpdateSegmentDueToActiveRadar() throws Exception {
    List<ModelError> modelErrorList =
        List.of(new ModelError("unable_to_save_active_radar", "can't be saved for active radar", null));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(segmentService)
        .save(any());

    MvcResult result = mockMvc.perform(post("/settings/segments/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/segments/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("can't be saved for active radar"));

    Mockito.verify(segmentService).save(any());
  }

  @Test
  public void shouldDeleteSegment() throws Exception {
    final SegmentDto segmentDto = new SegmentDto();
    segmentDto.setId(10L);
    segmentDto.setTitle("My segment");
    segmentDto.setDescription("My segment description");
    segmentDto.setPosition(1);

    Mockito.doAnswer((i) -> null).when(segmentService).deleteById(any());

    String url = String.format("/settings/segments/delete/%d", segmentDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/segments"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The segment has been deleted successfully."))
        .andReturn();

    Mockito.verify(segmentService).deleteById(segmentDto.getId());
  }
}
