package com.a5lab.axion.domain.radar_type;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public interface RadarTypeMapper extends PlainMapper<RadarType, RadarTypeDto> {
}
