package com.a5lab.axion.domain.ring;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.PlainMapper;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {TechnologyBlipMapper.class})
public abstract class RingMapper implements PlainMapper<Ring, RingDto> {

  @Autowired
  protected RadarRepository radarRepository;

  @Mapping(source = "radar.id", target = "radarId")
  @Mapping(source = "radar.title", target = "radarTitle")
  @Mapping(source = "technologyBlipList", target = "technologyBlipDtoList")
  public abstract RingDto toDto(final Ring entity);

  @Mapping(target = "radar", expression = "java(radarRepository.findById(dto.getRadarId()).get())")
  @Mapping(source = "technologyBlipDtoList", target = "technologyBlipList")
  public abstract Ring toEntity(final RingDto dto);
}
