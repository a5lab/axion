package com.a5lab.axion.domain.legal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MvcResult;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar_type.RadarTypeService;


@WebMvcTest(LegalController.class)
public class LegalControllerTests extends AbstractControllerTests {
  @MockBean
  private MessageSource messageSource;

  @Test
  public void shouldGetPrivacy() throws Exception {
    MvcResult result = mockMvc.perform(get("/legal/privacy"))
        .andExpect(status().isOk())
        .andExpect(view().name("legal/privacy"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("Legal privacy"));
  }

  @Test
  public void shouldGetTerms() throws Exception {
    MvcResult result = mockMvc.perform(get("/legal/terms"))
        .andExpect(status().isOk())
        .andExpect(view().name("legal/terms"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("Legal terms"));
  }

}
