package com.a5lab.axion.domain.radar;

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
  void shouldFindAllRadarsWithFilter() {
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
  void shouldFindByPrimaryRadarsIfPresent() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");
    radar.setPrimary(true);

    List<Radar> radarList = List.of(radar);
    Mockito.when(radarRepository.findByPrimary(any(boolean.class))).thenReturn(radarList);

    Optional<RadarDto> radarOptional = radarService.findByPrimary(radar.isPrimary());
    Assertions.assertTrue(radarOptional.isPresent());
    Assertions.assertEquals(radar.isPrimary(), radarOptional.isPresent());
    Assertions.assertEquals(radar.getId(), radarOptional.get().getId());
    Assertions.assertEquals(radar.getTitle(), radarOptional.get().getTitle());
    Assertions.assertEquals(radar.getDescription(), radarOptional.get().getDescription());

    Mockito.verify(radarRepository).findByPrimary((radar.isPrimary()));
  }

  @Test
  void shouldFindByPrimaryRadarsIfNotPresent() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");
    radar.setPrimary(false);

    List<Radar> radarList = List.of(radar);
    Mockito.when(radarRepository.findByPrimary(any(boolean.class))).thenReturn(radarList);

    Optional<RadarDto> radarOptional = radarService.findByPrimary(radar.isPrimary());
    Assertions.assertTrue(radarOptional.isPresent());
    Assertions.assertEquals(radar.isPrimary(), radarOptional.isEmpty());
    Assertions.assertEquals(radar.getId(), radarOptional.get().getId());
    Assertions.assertEquals(radar.getTitle(), radarOptional.get().getTitle());
    Assertions.assertEquals(radar.getDescription(), radarOptional.get().getDescription());

    Mockito.verify(radarRepository).findByPrimary((radar.isPrimary()));
  }

  @Test
  void shouldFindByPrimaryAndActiveRadars() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");
    radar.setPrimary(true);
    radar.setActive(true);

    List<Radar> radarList = List.of(radar);
    Mockito.when(radarRepository.findByPrimaryAndActive(any(boolean.class), any(boolean.class))).thenReturn(radarList);

    List<RadarDto> radarDtoOptional = radarService.findByPrimaryAndActive(radar.isPrimary(), radar.isActive());
    Assertions.assertFalse(radarDtoOptional.isEmpty());
    Assertions.assertEquals(radar.getId(), radarDtoOptional.get(0).getId());
    Assertions.assertEquals(radar.getTitle(), radarDtoOptional.get(0).getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDtoOptional.get(0).getDescription());

    Mockito.verify(radarRepository).findByPrimaryAndActive(radar.isPrimary(), radar.isActive());
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
    Mockito.when(radarTypeRepository.findById(any())).thenReturn(Optional.of(radarType));

    RadarDto radarDto = radarService.save(radarMapper.toDto(radar));
    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());

    Mockito.verify(radarRepository).save(any());
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


