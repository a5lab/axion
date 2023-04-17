package com.a5lab.axion.domain.radar;

import static org.mockito.ArgumentMatchers.any;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.a5lab.axion.domain.AbstractServiceTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.springframework.data.domain.Sort;

class RadarServiceTests extends AbstractServiceTests {
  private final RadarRepository radarRepository = Mockito.mock(RadarRepository.class);

  private final RadarMapper radarMapper = Mappers.getMapper(RadarMapper.class);

  private final RadarService radarService = new RadarServiceImpl(radarRepository, radarMapper);

  @Test
  void shouldFindAllRadars() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    List<Radar> radarList = List.of(radar);
    Mockito.when(radarRepository.findAll(any(Sort.class))).thenReturn(radarList);

    Collection<Radar> radarCollection = radarService.findAll();
    Assertions.assertEquals(1, radarCollection.size());
    Assertions.assertEquals(radarCollection.iterator().next().getId(), radar.getId());
    Assertions.assertEquals(radarCollection.iterator().next().getTitle(), radar.getTitle());
    Assertions.assertEquals(radarCollection.iterator().next().getDescription(), radar.getDescription());
  }

  @Test
  void shouldFindAllRadarsWithFilter() {
    /*
    final Radar radarDto = new Radar(10L, "my title", "my description");
    List<Radar> radarDtoList = List.of(radarDto);
    Page<Radar> page = new PageImpl<>(radarDtoList);
    Mockito.when(radarRepository.findAll(any(Sort.class), any())).thenReturn(page);

    RadarFilter radarFilter = new RadarFilter();
    Pageable pageable = new Pageable();

    Collection<RadarDto> radarDtoCollection = radarService.findAll(radarFilter, pageable);
    Assertions.assertEquals(1, radarDtoCollection.size());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getId(), radar.getId());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getTitle(), radar.getTitle());
    Assertions.assertEquals(radarDtoCollection.iterator().next().getDescription(), radar.getDescription());
    */
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

    Optional<Radar> radarOptional =  radarService.findByPrimary(radar.isPrimary());
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

    Optional<Radar> radarOptional = radarService.findByPrimary(radar.isPrimary());
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
  void shouldSaveRadar() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    Mockito.when(radarRepository.save(any())).thenReturn(radar);

    radarService.save(radar);
    Assertions.assertNotNull(radar.getId());
    Assertions.assertNotNull(radar.getTitle());
    Assertions.assertNotNull(radar.getDescription());

    Mockito.verify(radarRepository).save(radar);
  }

  @Test
  void shouldSaveRadarDto() {
    final Radar radar = new Radar();
    radar.setId(10L);
    radar.setRadarType(null);
    radar.setTitle("Radar title");
    radar.setDescription("Radar Description");

    Mockito.when(radarRepository.save(any())).thenReturn(radar);

    RadarDto radarDto = radarService.save(radarMapper.toDto(radar));
    Assertions.assertEquals(radar.getId(), radarDto.getId());
    Assertions.assertEquals(radar.getTitle(), radarDto.getTitle());
    Assertions.assertEquals(radar.getDescription(), radarDto.getDescription());

    Mockito.verify(radarRepository).save(any());
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


