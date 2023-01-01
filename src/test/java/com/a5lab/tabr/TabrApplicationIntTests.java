package com.a5lab.tabr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TabrApplicationIntTests {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void index() throws Exception {
    mockMvc.perform(get("/")).andExpect(status().isOk());
  }
}
