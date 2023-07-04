package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.FlashMessages;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar_type.RadarTypeDto;
import com.a5lab.axion.domain.radar_type.RadarTypeService;

@WebMvcTest(RadarCfgController.class)
public class RadarCfgControllerTests extends AbstractControllerTests {
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

    MvcResult result = mockMvc.perform(get("/settings/radars"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/index"))
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

    String url = String.format("/settings/radars/show/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findById(radarDto.getId());
  }

  @Test
  public void shouldRedirectShowRadar() throws Exception {
    Mockito.when(radarService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/radars/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid radar id."))
        .andReturn();

    Mockito.verify(radarService).findById(any());
  }

  @Test
  public void shouldAddRadar() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/radars/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/add"))
        .andExpect(model().attributeExists("radarDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("description"));
  }

  @Test
  public void shouldCreateRadar() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setDescription("My Description");
    radarTypeDto.setTitle("My title");
    radarTypeDto.setCode("My code");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.save(any(RadarDto.class))).thenReturn(radarDto);

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been created successfully."))
        .andReturn();

    Mockito.verify(radarService).save(any(RadarDto.class));
  }


  @Test
  public void shouldFailToCreateRadarDueToEmptyTitle() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldFailToCreateRadarDueToNotUniqueTitle() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "is already taken", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("is already taken"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldFailToCreateRadarDueToNotOnePrimary() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "should be only one primary radar", "primary"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("primary"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldEditRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);
    Mockito.when(radarService.findById(any())).thenReturn(Optional.of(radarDto));

    String url = String.format("/settings/radars/edit/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findById(radarDto.getId());
  }

  @Test
  public void shouldRedirectEditRadar() throws Exception {
    Mockito.when(radarService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/radars/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid radar id."))
        .andReturn();

    Mockito.verify(radarService).findById(any());
  }

  @Test
  public void shouldUpdateRadar() throws Exception {
    final RadarTypeDto radarTypeDto = new RadarTypeDto();
    radarTypeDto.setId(10L);
    radarTypeDto.setDescription("My Description");
    radarTypeDto.setTitle("My title");
    radarTypeDto.setCode("My code");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(radarTypeDto.getId());
    radarDto.setRadarTypeTitle(radarTypeDto.getTitle());
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.save(any(RadarDto.class))).thenReturn(radarDto);

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been updated successfully."))
        .andReturn();

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldFailToUpdateRadarDueToEmptyTitle() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type title");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldFailToUpdateRadarDueToNotUniqueTitle() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "is already taken", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("is already taken"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldFailToUpdateRadarDueToNotOnePrimary() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<ModelError> modelErrorList = List.of(new ModelError(null, "should be only one primary radar", "primary"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(radarService).save(any(RadarDto.class));

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarTypeId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("primary"));

    Mockito.verify(radarService).save(any(RadarDto.class));
  }

  @Test
  public void shouldDeleteRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarTypeId(3L);
    radarDto.setRadarTypeTitle("My radar type title");
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.doAnswer((i) -> null).when(radarService).deleteById(any());

    String url = String.format("/settings/radars/delete/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(
            MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been deleted successfully."))
        .andReturn();

    Mockito.verify(radarService).deleteById(radarDto.getId());
  }
}
