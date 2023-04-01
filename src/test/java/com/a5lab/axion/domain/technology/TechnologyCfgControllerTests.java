package com.a5lab.axion.domain.technology;

import com.a5lab.axion.domain.AbstractControllerTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    Mockito.when(technologyService.findById(technologyDto.getId())).thenReturn(Optional.of(technologyDto));

    String url = String.format("/settings/technologies/show/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(technologyDto.getTitle()));
    Assertions.assertTrue(content.contains(technologyDto.getDescription()));
  }

  @Test
  public void shouldRedirectShowTechnology() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technologies/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andReturn();
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
  /*
  @Test
  public void shouldFailToCreateTechnologyOnLowerCaseTitle() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto(10L,  "My title", "My website", "My description", 0, true);

    MvcResult result = mockMvc.perform(post("/settings/technologies/create")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .param("description", technologyDto.getDescription())
                    .param("title", technologyDto.getTitle())
                    .param("website", technologyDto.getWebsite())
                    .sessionAttr("technologyDto", technologyDto))
            .andExpect(status().is3xxRedirection())
            .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("should be uppercase"));
  }

  @Test
  public void shouldCreateRing() throws Exception {
    final RingDto ringDto = new RingDto(10L, null, "MY TITLE", "MY DESCRIPTION", 1, true);

    MvcResult result = mockMvc.perform(post("/settings/rings/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", ringDto.getTitle())
            .param("description", ringDto.getDescription())
            .sessionAttr("ringDto", ringDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/rings"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToCreateTechnologyOnBlankDescription() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto(10L, "My title", "My website", "My description", 0, true);

    MvcResult result = mockMvc.perform(post("/settings/technologies/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", technologyDto.getTitle())
            .param("website", technologyDto.getWebsite())
            .param("description", technologyDto.getDescription())
            .sessionAttr("technologyDto", technologyDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }
   */
  @Test
  public void shouldEditTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);
    Mockito.when(technologyService.findById(technologyDto.getId())).thenReturn(Optional.of(technologyDto));

    String url = String.format("/settings/technologies/edit/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains(technologyDto.getTitle()));
    Assertions.assertTrue(content.contains(technologyDto.getDescription()));
  }
  @Test
  public void shouldRedirectEditTechnology() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/technologies/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andReturn();
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

    MvcResult result = mockMvc.perform(post("/settings/technologies/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", technologyDto.getTitle())
            .param("website", technologyDto.getWebsite())
            .param("description", technologyDto.getDescription())
            .sessionAttr("technologyDto", technologyDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }

  @Test
  public void shouldFailToUpdateTechnology() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setWebsite("My website");
    technologyDto.setTitle("My technology");
    technologyDto.setDescription("My technology description");
    technologyDto.setMoved(0);
    technologyDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/technologies/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", technologyDto.getTitle())
            .param("website", technologyDto.getWebsite())
            .sessionAttr("technologyDto", technologyDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
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

    String url = String.format("/settings/technologies/delete/%d", technologyDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/technologies"))
        .andReturn();
  }

}
