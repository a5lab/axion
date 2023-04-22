package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public abstract class TechnologyBlipMapper
    extends CycleAvoidingEntityToDtoMapper<TechnologyBlip, TechnologyBlipDto> {
}
