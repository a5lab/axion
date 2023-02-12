package com.a5lab.axion.domain.entries;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface EntryMapper extends EntityToDtoMapper<Entry, EntryDto> {
}
