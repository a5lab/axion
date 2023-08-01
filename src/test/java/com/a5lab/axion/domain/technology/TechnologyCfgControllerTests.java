package com.a5lab.axion.domain.technology;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.FlashMessages;
import com.a5lab.axion.domain.ModelError;
import com.a5lab.axion.domain.ValidationException;

@WebMvcTest(TechnologyCfgController.class)
public class TechnologyCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private TechnologyService technologyService;

  @Test
  public void shouldGetTechnologies() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    List<TechnologyDto> technologyList = List.of(technologyDto);
    Page<TechnologyDto> page = new PageImpl<>(technologyList);
    Mockito.when(technologyService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/technologies"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technologies/index"))
        .andExpect(model().attributeExists("technologyDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(technologyDto.getTitle()));
    Assertions.assertTrue(content.contains(technologyDto.getWebsite()));
    Assertions.assertTrue(content.contains(technologyDto.getDescription()));

    Mockito.verify(technologyService).findAll(any(), any());
  }

  @Test
  public void shouldShowTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    Mockito.when(technologyService.findById(any())).thenReturn(Optional.of(technologyDto));

    String url = String.format("/settings/technologies/show/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technologies/show"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(technologyDto.getTitle()));
    Assertions.assertTrue(content.contains(technologyDto.getDescription()));

    Mockito.verify(technologyService).findById(technologyDto.getId());
  }

  @Test
  public void shouldRedirectShowTechnology() throws Exception {
    Mockito.when(technologyService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/technologies/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid technology id."))
        .andReturn();

    Mockito.verify(technologyService).findById(any());
  }

  @Test
  public void shouldAddTechnology() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technologies/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technologies/add"))
        .andExpect(model().attributeExists("technologyDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("website"));
    Assertions.assertTrue(content.contains("description"));
  }


  @Test
  public void shouldCreateTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    Mockito.when(technologyService.save(any())).thenReturn(technologyDto);

    MvcResult result = mockMvc.perform(post("/settings/technologies/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("website", technologyDto.getWebsite())
            .param("title", technologyDto.getTitle())
            .param("description", technologyDto.getDescription())
            .sessionAttr("technologyDto", technologyDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology has been created successfully."))
        .andReturn();

    Mockito.verify(technologyService).save(any());
  }

  @Test
  public void shouldFailToCreateTechnologyDueToEmptyTitle() throws Exception {
    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(technologyService)
        .save(any(TechnologyDto.class));

    MvcResult result = mockMvc.perform(post("/settings/technologies/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", ""))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("technologyDto", "title"))
        .andExpect(view().name("settings/technologies/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(technologyService).save(any(TechnologyDto.class));
  }

  @Test
  public void shouldFailToCreateTechnologyDueToTitleWithWhiteSpace() throws Exception {
    List<ModelError> modelErrorList = List.of(
        new ModelError(null, "should be without whitespaces before and after", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(technologyService)
        .save(any(TechnologyDto.class));

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/settings/technologies/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", " My title "))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("technologyDto", "title"))
        .andExpect(view().name("settings/technologies/add"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("should be without whitespaces before and after"));

    Mockito.verify(technologyService).save(any(TechnologyDto.class));
  }

  @Test
  public void shouldEditTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    Mockito.when(technologyService.findById(any())).thenReturn(Optional.of(technologyDto));

    String url = String.format("/settings/technologies/edit/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/technologies/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(technologyDto.getTitle()));
    Assertions.assertTrue(content.contains(technologyDto.getDescription()));

    Mockito.verify(technologyService).findById(technologyDto.getId());
  }

  @Test
  public void shouldRedirectEditTechnology() throws Exception {
    Mockito.when(technologyService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/technologies/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid technology id."))
        .andReturn();

    Mockito.verify(technologyService).findById(any());
  }

  @Test
  public void shouldUpdateTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    Mockito.when(technologyService.save(any())).thenReturn(technologyDto);

    MvcResult result = mockMvc.perform(post("/settings/technologies/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", technologyDto.getTitle())
            .param("website", technologyDto.getWebsite())
            .param("description", technologyDto.getDescription())
            .sessionAttr("technologyDto", technologyDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology has been updated successfully."))
        .andReturn();

    Mockito.verify(technologyService).save(any());
  }

  @Test
  public void shouldFailToUpdateTechnologyDueToEmptyTitle() throws Exception {
    List<ModelError> modelErrorList = List.of(new ModelError(null, "must not be blank", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(technologyService)
        .save(any(TechnologyDto.class));

    MvcResult result = mockMvc.perform(post("/settings/technologies/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", ""))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("technologyDto", "title"))
        .andExpect(view().name("settings/technologies/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));

    Mockito.verify(technologyService).save(any(TechnologyDto.class));
  }

  @Test
  public void shouldFailToUpdateTechnologyDueToTitleWithWhiteSpace() throws Exception {
    List<ModelError> modelErrorList = List.of(
        new ModelError(null, "should be without whitespaces before and after", "title"));
    String errorMessage = ValidationException.buildErrorMessage(modelErrorList);
    Mockito.doThrow(new ValidationException(errorMessage, modelErrorList)).when(technologyService)
        .save(any(TechnologyDto.class));

    MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/settings/technologies/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", " My title "))
        .andExpect(status().isOk())
        .andExpect(model().attributeHasFieldErrors("technologyDto", "title"))
        .andExpect(view().name("settings/technologies/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("should be without whitespaces before and after"));

    Mockito.verify(technologyService).save(any(TechnologyDto.class));
  }

  @Test
  public void shouldDeleteTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    Mockito.doAnswer((i) -> null).when(technologyService).deleteById(any());

    String url = String.format("/settings/technologies/delete/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andExpect(MockMvcResultMatchers.flash()
            .attribute(FlashMessages.INFO, "The technology has been deleted successfully."))
        .andReturn();

    Mockito.verify(technologyService).deleteById(technologyDto.getId());
  }
}
