package com.a5lab.tabr.domain.blips;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface BlipMapper extends CycleAvoidingEntityToDtoMapper<Blip, BlipDto> {
}
