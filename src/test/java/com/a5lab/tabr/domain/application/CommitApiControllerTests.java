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

@WebMvcTest(CommitApiController.class)
public class CommitApiControllerTests extends AbstractControllerTests {

  @Test
  public void shouldShowCommit() throws Exception {
    mockMvc.perform(get("/api/v1/application/commit/show").contentType(APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isMap())
        .andExpect(jsonPath("$").value(hasKey("branch")))
        .andExpect(jsonPath("$").value(hasKey("buildTime")))
        .andExpect(jsonPath("$").value(hasKey("buildVersion")))
        .andExpect(jsonPath("$").value(hasKey("commitIdAbbrev")))
        .andExpect(jsonPath("$").value(hasKey("commitIdFull")))
        .andExpect(jsonPath("$").value(hasKey("commitMessageFull")))
        .andExpect(jsonPath("$").value(hasKey("commitMessageShort")))
        .andExpect(jsonPath("$").value(hasKey("commitTime")))
        .andExpect(jsonPath("$").value(hasKey("dirty")))
        .andExpect(jsonPath("$").value(hasKey("tags")));
  }
}
