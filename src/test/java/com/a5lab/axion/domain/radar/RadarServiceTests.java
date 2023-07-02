package com.a5lab.axion.domain.radar;

import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;

import java.util.Collection;
import java.util.LinkedList;
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
import com.a5lab.axion.domain.ValidationException;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;

class RadarServiceTests extends AbstractServiceTests {
  @MockBean
  private RadarTypeRepository radarTypeRepository;
  @MockBean
  private RadarRepository radarRepository;
  @Autowired
  private RadarMapper radarMapper;
  @Autowired
  private RadarService radarService;

  @Test
  void shouldFindAllRadars() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    List<Radar> radarList = List.of(radar);
    Mockito.when(radarRepository.findAll(any(Sort.class))).thenReturn(radarList);

    Collection<RadarDto> radarDtoCollection = radarService.findAll();
    Assertions.assertEquals(1, radarDtoCollection.size());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getId(), radar.getId());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getDescription(), radar.getDescription());
  }

  @Test
  void shouldFindAllRadarsWithEmptyFilter() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    List<Radar> radarList = List.of(radar);
    Page<Radar> page = new PageImpl<>(radarList);
    Mockito.when(radarRepository.findAll(ArgumentMatchers.<Specification<Radar>>any(), any(Pageable.class)))
        .thenReturn(page);

    RadarFilter radarFilter = new RadarFilter();
    Pageable pageable = PageRequest.of(0, 10, Sort.by("title, asc"));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(1, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(radarDtoPage.iterator().next().getId(), radar.getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(), radar.getDescription());

    // Mockito.verify(radarRepository).findAll(Specification.allOf((root, query, criteriaBuilder) -> null), pageable);
  }

  @Test
  void shouldFindAllRadarsWithFullFilter() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    List<Radar> radarList = List.of(radar);
    Page<Radar> page = new PageImpl<>(radarList);
    Mockito.when(radarRepository.findAll(ArgumentMatchers.<Specification<Radar>>any(), any(Pageable.class)))
        .thenReturn(page);

    RadarFilter radarFilter = new RadarFilter();
    radarFilter.setTitle(radar.getTitle());
    radarFilter.setFilterByPrimary(true);
    radarFilter.setPrimary(true);
    radarFilter.setFilterByActive(true);
    radarFilter.setActive(true);

    Pageable pageable = PageRequest.of(0, 10, Sort.by("title, asc"));
    Page<RadarDto> radarDtoPage = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(1, radarDtoPage.getSize());
    Assertions.assertEquals(0, radarDtoPage.getNumber());
    Assertions.assertEquals(1, radarDtoPage.getTotalPages());
    Assertions.assertEquals(radarDtoPage.iterator().next().getId(), radar.getId());
    Assertions.assertEquals(radarDtoPage.iterator().next().getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDtoPage.iterator().next().getDescription(), radar.getDescription());

    // Mockito.verify(radarRepository).findAll(Specification.allOf((root, query, criteriaBuilder) -> null), pageable);
  }

  @Test
  void shouldFindByIdRadar() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    Mockito.when(radarRepository.findById(radar.getId())).thenReturn(Optional.of(radar));

    Optional<RadarDto> radarDtoOptional = radarService.findById(radar.getId());
    Assertions.assertTrue(radarDtoOptional.isPresent());
    Assertions.assertEquals(radar.getId(), radarDtoOptional.get().getId());
    Assertions.assertEquals(radar.getTitle(), radarDtoOptional.get().getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDtoOptional.get().getDescription());

    Mockito.verify(radarRepository).findById(radar.getId());
  }

  @Test
  void shouldSaveRadarDto() {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);

    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(radarType);
    radar.setTitle("Radar title");
    radar.setDescription("Radar description");

    Mockito.when(radarRepository.save(any())).thenReturn(radar);
    Mockito.when(radarRepository.findByPrimary(anyBoolean())).thenReturn(new LinkedList<>());
    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    RadarDto radarDto = radarService.save(radarMapper.toDto(radar));
    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());

    Mockito.verify(radarRepository).save(any());
    Mockito.verify(radarRepository).findByPrimary(true);
    Mockito.verify(radarTypeRepository).findById(radarType.getId());
  }

  @Test
  void shouldFailToTheSaveSecondPrimaryRadarDto() {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);

    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(radarType);
    radar.setTitle("Radar title");
    radar.setPrimary(true);
    radar.setActive(true);
    radar.setDescription("Radar description");

    final Radar radar1 = new Radar();
    radar1.setId(12L);
    radar1.setRadarType(radarType);
    radar1.setTitle("Radar title");
    radar1.setPrimary(true);
    radar1.setActive(true);
    radar1.setDescription("Radar description");
    List<Radar> radarList = List.of(radar1);

    Mockito.when(radarRepository.save(any())).thenReturn(radar);
    Mockito.when(radarRepository.findByPrimary(anyBoolean())).thenReturn(radarList);
    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    ValidationException exception =
        catchThrowableOfType(() -> radarService.save(radarMapper.toDto(radar)), ValidationException.class);
    Assertions.assertFalse(exception.getMessage().isEmpty());
  }

  @Test
  void shouldSucceedToSavePrimaryRadarDtoWithNonPrimaryRadar() throws Exception {
    final RadarType radarType = new RadarType();
    radarType.setId(1L);

    final Radar radar = new Radar();
    radar.setId(2L);
    radar.setRadarType(radarType);
    radar.setTitle("Radar title");
    radar.setPrimary(true);
    radar.setActive(true);
    radar.setDescription("Radar description");

    final Radar radar1 = new Radar();
    radar1.setId(3L);
    radar1.setRadarType(radarType);
    radar1.setTitle("Radar title");
    radar1.setPrimary(false);
    radar1.setActive(false);
    radar1.setDescription("Radar description");
    List<Radar> radarList = List.of(radar1);

    Mockito.when(radarRepository.save(any())).thenReturn(radar);
    Mockito.when(radarRepository.findByPrimary(anyBoolean())).thenReturn(radarList);
    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    RadarDto radarDto = radarService.save(radarMapper.toDto(radar));
    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());

    Mockito.verify(radarRepository).save(any());
    Mockito.verify(radarRepository).findByPrimary(true);
    Mockito.verify(radarTypeRepository).findById(radarType.getId());
  }

  @Test
  void shouldDeleteRadar() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    Mockito.doAnswer((i) -> null).when(radarRepository).deleteById(radar.getId());

    radarService.deleteById(radar.getId());
    Mockito.verify(radarRepository).deleteById(radar.getId());
  }
}


