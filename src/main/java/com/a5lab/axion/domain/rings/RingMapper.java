package com.a5lab.axion.domain.rings;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface RingMapper extends EntityToDtoMapper<Ring, RingDto> {
}
