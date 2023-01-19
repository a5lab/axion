package com.a5lab.tabr.domain.entities;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface EntityMapper extends EntityToDtoMapper<Entity, EntityDto> {
}
