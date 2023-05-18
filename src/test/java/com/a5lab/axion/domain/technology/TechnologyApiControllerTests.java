package com.a5lab.axion.domain.technology;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.a5lab.axion.domain.AbstractControllerTests;

@WebMvcTest(TechnologyApiController.class)
public class TechnologyApiControllerTests extends AbstractControllerTests {
  @MockBean
  private TechnologyService technologyService;

  @Test
  public void shouldGetTechnologies() throws Exception {
    final TechnologyDto technologyDto = new TechnologyDto();
    technologyDto.setId(10L);
    technologyDto.setTitle("My title");
    technologyDto.setDescription("My description");
    technologyDto.setWebsite("My website");
    technologyDto.setMoved(1);
    technologyDto.setActive(true);

    Page<TechnologyDto> technologyDtoPage = new PageImpl<>(Arrays.asList(technologyDto));
    Mockito.when(technologyService.findAll(any(), any())).thenReturn(technologyDtoPage);

    mockMvc.perform(get("/api/v1/technologies").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(technologyDtoPage.getContent().size())))
        .andExpect(jsonPath("$[0].id", equalTo(technologyDto.getId()), Long.class))
        .andExpect(jsonPath("$[0].title", equalTo(technologyDto.getTitle())))
        .andExpect(jsonPath("$[0].description", equalTo(technologyDto.getDescription())))
        .andExpect(jsonPath("$[0].website", equalTo(technologyDto.getWebsite())))
        .andExpect(jsonPath("$[0].moved", equalTo(technologyDto.getMoved()), int.class))
        .andExpect(jsonPath("$[0].active", equalTo(technologyDto.isActive())));
  }
}
