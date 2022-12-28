package com.a5lab.tabr.controllers;

import com.a5lab.tabr.controllers.HomeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(HomeController.class)
class HomeControllerTests {
  @Autowired
  private MockMvc mockMvc;

  @Test
  void index() throws Exception {
    mockMvc.perform(get("/home")).andExpect(status().isOk());
  }
}
