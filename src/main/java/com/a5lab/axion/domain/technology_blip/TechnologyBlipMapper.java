package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.CycleTrackingMapper;

@Mapper(config = MapperConfiguration.class)
public abstract class TechnologyBlipMapper
    extends CycleTrackingMapper<TechnologyBlip, TechnologyBlipDto> {
}
