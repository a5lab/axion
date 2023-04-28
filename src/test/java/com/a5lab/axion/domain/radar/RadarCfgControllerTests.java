package com.a5lab.axion.domain.radar;

import com.a5lab.axion.domain.AbstractControllerTests;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeService;

import com.a5lab.axion.utils.FlashMessages;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RadarCfgController.class)
public class RadarCfgControllerTests extends AbstractControllerTests {
  @MockBean
  private RadarService radarService;

  @MockBean
  private RadarTypeService radarTypeService;

  @Test
  public void shouldGetRadars() throws Exception {
    //Create radarType
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setDescription("My Description");
    radarType.setTitle("My title");
    radarType.setCode("My code");

    //Create radar for radarType
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(radarType);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    List<RadarDto> radarDtoList = List.of(radarDto);
    Page<RadarDto> page = new PageImpl<>(radarDtoList);
    Mockito.when(radarService.findAll(any(), any())).thenReturn(page);

    MvcResult result = mockMvc.perform(get("/settings/radars"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/index"))
        .andExpect(model().attributeExists("radarDtoPage"))
        .andExpect(model().attributeExists("pageNumbers"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findAll(any(), any());
  }

  @Test
  public void shouldShowRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(null);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.findById(any())).thenReturn(Optional.of(radarDto));

    String url = String.format("/settings/radars/show/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findById(radarDto.getId());
  }

  @Test
  public void shouldRedirectShowRadar() throws Exception {
    Mockito.when(radarService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/radars/show/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid radar id."))
        .andReturn();

    Mockito.verify(radarService).findById(any());
  }

  @Test
  public void shouldAddRadar() throws Exception {
    MvcResult result = mockMvc.perform(get("/settings/radars/add"))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/add"))
        .andExpect(model().attributeExists("radarDto"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("title"));
    Assertions.assertTrue(content.contains("description"));
  }

  @Test
  public void shouldCreateRadar() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setDescription("My Description");
    radarType.setTitle("My title");
    radarType.setCode("My code");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(radarType);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.save(radarDto)).thenReturn(radarDto);

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarType().getId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been created successfully."))
        .andReturn();

    Mockito.verify(radarService).save((RadarDto) any());
  }

  @Test
  public void shouldFailToCreateRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(null);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/radars/create")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", radarDto.getTitle())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldEditRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(null);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);
    Mockito.when(radarService.findById(radarDto.getId())).thenReturn(Optional.of(radarDto));

    String url = String.format("/settings/radars/edit/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains(radarDto.getTitle()));
    Assertions.assertTrue(content.contains(radarDto.getDescription()));

    Mockito.verify(radarService).findById(radarDto.getId());
  }

  @Test
  public void shouldRedirectEditRadar() throws Exception {
    Mockito.when(radarService.findById(any())).thenReturn(Optional.empty());

    MvcResult result = mockMvc.perform(get("/settings/radars/edit/1"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.ERROR, "Invalid radar id."))
        .andReturn();

    Mockito.verify(radarService).findById(any());
  }

  @Test
  public void shouldUpdateRadar() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setDescription("My Description");
    radarType.setTitle("My title");
    radarType.setCode("My code");

    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(radarType);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.when(radarService.save(radarDto)).thenReturn(radarDto);

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("radarType.id", String.valueOf(radarDto.getRadarType().getId()))
            .param("title", radarDto.getTitle())
            .param("description", radarDto.getDescription())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been updated successfully."))
        .andReturn();

    Mockito.verify(radarService).save((RadarDto) any());
  }

  @Test
  public void shouldFailToUpdateRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(null);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    MvcResult result = mockMvc.perform(post("/settings/radars/update")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("title", radarDto.getTitle())
            .sessionAttr("radarDto", radarDto))
        .andExpect(status().isOk())
        .andExpect(view().name("settings/radars/edit"))
        .andReturn();

    String content = result.getResponse().getContentAsString();
    Assertions.assertTrue(content.contains("must not be blank"));
  }

  @Test
  public void shouldDeleteRadar() throws Exception {
    final RadarDto radarDto = new RadarDto();
    radarDto.setId(10L);
    radarDto.setRadarType(null);
    radarDto.setTitle("My title");
    radarDto.setDescription("My description");
    radarDto.setPrimary(true);
    radarDto.setActive(true);

    Mockito.doAnswer((i) -> null).when(radarService).deleteById(radarDto.getId());

    String url = String.format("/settings/radars/delete/%d", radarDto.getId());
    MvcResult result = mockMvc.perform(get(url))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/settings/radars"))
        .andExpect(MockMvcResultMatchers.flash().attribute(FlashMessages.INFO, "The radar has been deleted successfully."))
        .andReturn();

    Mockito.verify(radarService).deleteById(radarDto.getId());
  }
}
