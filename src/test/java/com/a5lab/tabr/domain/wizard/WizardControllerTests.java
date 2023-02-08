package com.a5lab.tabr.domain.wizard;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.a5lab.tabr.AbstractControllerTests;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.wizard.WizardController;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(WizardController.class)
public class WizardControllerTests extends AbstractControllerTests {

  // @MockBean
  // private WizardService wizardService;

  @Test
  public void shouldAddTentant() throws Exception {
    MvcResult result = mockMvc.perform(get("/wizard/add"))
        .andExpect(status().isOk())
        .andReturn();
    String content = result.getResponse().getContentAsString();

    Assertions.assertTrue(content.contains("Title"));
    Assertions.assertTrue(content.contains("Description"));
  }

  @Test
  public void shouldCreateTenant() throws Exception {
    final Radar radar = new Radar();
    // final Radar radar = new Radar(10L, "my title", "my description");

    MvcResult result = mockMvc.perform(post("/wizard/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", radar.getTitle())
            .param("description", radar.getDescription())
            .sessionAttr("tenantDto", radar))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/tenants"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
  }


}
