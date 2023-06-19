package com.a5lab.axion.domain.radar_type;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import com.a5lab.axion.domain.AbstractServiceTests;
import com.a5lab.axion.domain.radar.RadarMapper;
import com.a5lab.axion.domain.radar.RadarService;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class RadarTypeServiceTests extends AbstractServiceTests {
  @MockBean
  private RadarTypeRepository radarTypeRepository;

  @Autowired
  private RadarTypeMapper radarTypeMapper;

  @Autowired
  private RadarTypeService radarTypeService;


  @Test
  void shouldFindAllRadarTypes() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setTitle("Radar type title");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setDescription("Radar type description");

    List<RadarType> radarTypeList = List.of(radarType);
    Mockito.when(radarTypeRepository.findAll(any(Sort.class))).thenReturn(radarTypeList);

    Collection<RadarTypeDto> radarTypeDtoCollection = radarTypeService.findAll();
    Assertions.assertEquals(1, radarTypeDtoCollection.size());
    Assertions.assertEquals(radarTypeDtoCollection.iterator().next().getId(), radarType.getId());
    Assertions.assertEquals(radarTypeDtoCollection.iterator().next().getTitle(), radarType.getTitle());
    Assertions.assertEquals(radarTypeDtoCollection.iterator().next().getCode(), radarType.getCode());
    Assertions.assertEquals(radarTypeDtoCollection.iterator().next().getDescription(), radarType.getDescription());
  }

  @Test
  void shouldFindAllRadarTypesWithEmptyFilter() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setTitle("Radar type title");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setDescription("Radar type description");

    List<RadarType> radarTypeList = List.of(radarType);
    Page<RadarType> page = new PageImpl<>(radarTypeList);
    Mockito.when(radarTypeRepository.findAll(any(Pageable.class))).thenReturn(page);

    Pageable pageable = PageRequest.of(0, 10, Sort.by("title, asc"));
    Page<RadarTypeDto> radarDtoPage = radarTypeService.findAll(pageable);
    Assertions.assertEquals(1, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(radarDtoPage.iterator().next().getId(), radarType.getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radarType.getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getCode(), radarType.getCode());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(), radarType.getDescription());

    Mockito.verify(radarTypeRepository).findAll(pageable);
  }

  @Test
  void shouldFindByIdRadarType() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setTitle("Radar type title");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setDescription("Radar type description");

    Mockito.when(radarTypeRepository.findById(radarType.getId())).thenReturn(Optional.of(radarType));

    Optional<RadarTypeDto> radarDtoOptional = radarTypeService.findById(radarType.getId());
    Assertions.assertTrue(radarDtoOptional.isPresent());
    Assertions.assertEquals(radarType.getId(), radarDtoOptional.get().getId());
    Assertions.assertEquals(radarType.getTitle(), radarDtoOptional.get().getTitle());
    Assertions.assertEquals(radarType.getCode(), radarDtoOptional.get().getCode());
    Assertions.assertEquals(radarType.getDescription(), radarDtoOptional.get().getDescription());

    Mockito.verify(radarTypeRepository).findById(radarType.getId());
  }

  @Test
  void shouldFindByCodeRadarType() {
    final RadarType radarType = new RadarType();
    radarType.setId(10L);
    radarType.setTitle("Radar type title");
    radarType.setCode(RadarType.TECHNOLOGY_RADAR);
    radarType.setDescription("Radar type description");

    Mockito.when(radarTypeRepository.findByCode(radarType.getCode())).thenReturn(Optional.of(radarType));

    Optional<RadarTypeDto> radarDtoOptional = radarTypeService.findByCode(radarType.getCode());
    Assertions.assertTrue(radarDtoOptional.isPresent());
    Assertions.assertEquals(radarType.getId(), radarDtoOptional.get().getId());
    Assertions.assertEquals(radarType.getTitle(), radarDtoOptional.get().getTitle());
    Assertions.assertEquals(radarType.getCode(), radarDtoOptional.get().getCode());
    Assertions.assertEquals(radarType.getDescription(), radarDtoOptional.get().getDescription());

    Mockito.verify(radarTypeRepository).findByCode(radarType.getCode());
  }
}


