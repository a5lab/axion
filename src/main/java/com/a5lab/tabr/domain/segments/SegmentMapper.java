package com.a5lab.tabr.domain.segments;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface SegmentMapper extends EntityToDtoMapper<Segment, SegmentDto> {
}
