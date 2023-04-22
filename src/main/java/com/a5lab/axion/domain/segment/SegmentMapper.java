package com.a5lab.axion.domain.segment;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public interface SegmentMapper extends PlainMapper<Segment, SegmentDto> {
}
