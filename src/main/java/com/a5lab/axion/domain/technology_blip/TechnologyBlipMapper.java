package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class, componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TechnologyBlipMapper extends PlainMapper<TechnologyBlip, TechnologyBlipDto> {
}

