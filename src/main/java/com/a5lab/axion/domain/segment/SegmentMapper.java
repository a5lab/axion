package com.a5lab.axion.domain.segment;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    typeConversionPolicy = ReportingPolicy.WARN)
public interface SegmentMapper extends PlainMapper<Segment, SegmentDto> {
}
