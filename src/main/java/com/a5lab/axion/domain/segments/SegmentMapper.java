package com.a5lab.axion.domain.segments;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface SegmentMapper extends EntityToDtoMapper<Segment, SegmentDto> {
}
