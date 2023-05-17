package com.a5lab.axion.domain.radar_type;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.radar.RadarMapper;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {RadarMapper.class})
public abstract class RadarTypeMapper implements PlainMapper<RadarType, RadarTypeDto> {

  @Mapping(source = "radarList", target = "radarDtoList")
  public abstract RadarTypeDto toDto(final RadarType entity);

  @Mapping(source = "radarDtoList", target = "radarList")
  public abstract RadarType toEntity(final RadarTypeDto dto);
}
