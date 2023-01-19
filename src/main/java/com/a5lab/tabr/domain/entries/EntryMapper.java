package com.a5lab.tabr.domain.entries;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface EntryMapper extends EntityToDtoMapper<Entry, EntryDto> {
}
