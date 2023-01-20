package com.a5lab.tabr.domain.application;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.a5lab.tabr.AbstractControllerTests;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(VersionApiController.class)
public class VersionApiControllerTests extends AbstractControllerTests {

  @Test
  public void shouldShowVersion() throws Exception {
    mockMvc.perform(get("/api/v1/application/version/show").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$").value(hasKey("groupId")))
        .andExpect(jsonPath("$").value(hasKey("artifactId")))
        .andExpect(jsonPath("$").value(hasKey("version")))
        .andExpect(jsonPath("$").value(hasKey("name")))
        .andExpect(jsonPath("$").value(hasKey("description")));
  }
}
