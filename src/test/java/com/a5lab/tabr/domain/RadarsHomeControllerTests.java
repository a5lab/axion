package com.a5lab.tabr.domain;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.a5lab.tabr.AbstractControllerTests;
import com.a5lab.tabr.domain.radars.RadarsHomeController;

@WebMvcTest(RadarsHomeController.class)
public class RadarsHomeControllerTests extends AbstractControllerTests {
  @Test
  public void shouldGetHome() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
  }
}
