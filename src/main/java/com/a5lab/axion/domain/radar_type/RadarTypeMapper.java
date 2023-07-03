package com.a5lab.axion.domain.radar_type;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public abstract class RadarTypeMapper implements PlainMapper<RadarType, RadarTypeDto> {

  public abstract RadarTypeDto toDto(final RadarType entity);

  @Mapping(target = "radarList", ignore = true)
  public abstract RadarType toEntity(final RadarTypeDto dto);
}
