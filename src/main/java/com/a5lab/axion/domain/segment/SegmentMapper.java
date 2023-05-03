package com.a5lab.axion.domain.segment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    typeConversionPolicy = ReportingPolicy.WARN)
public abstract class SegmentMapper implements PlainMapper<Segment, SegmentDto> {
  @Autowired
  protected RadarRepository radarRepository;

  @Mapping(source = "radar.id", target = "radarId")
  @Mapping(source = "radar.title", target = "radarTitle")
  public abstract SegmentDto toDto(final Segment entity);

  @Mapping(target = "radar", expression = "java(radarRepository.findById(dto.getRadarId()).get())")
  public abstract Segment toEntity(final SegmentDto dto);
  
}
