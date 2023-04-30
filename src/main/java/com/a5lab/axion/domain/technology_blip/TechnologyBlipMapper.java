package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    typeConversionPolicy = ReportingPolicy.WARN)
public interface TechnologyBlipMapper extends PlainMapper<TechnologyBlip, TechnologyBlipDto> {
}

