package com.a5lab.tabr.domain.radars;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public abstract class RadarMapper extends CycleAvoidingEntityToDtoMapper<Radar, RadarDto> {
}
