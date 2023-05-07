package com.a5lab.axion.domain.radar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    unmappedTargetPolicy = ReportingPolicy.WARN,
    typeConversionPolicy = ReportingPolicy.WARN)
public abstract class RadarMapper implements PlainMapper<Radar, RadarDto> {

  @Autowired
  protected RadarTypeRepository radarTypeRepository;

  @Mapping(source = "radarType.id", target = "radarTypeId")
  @Mapping(source = "radarType.title", target = "radarTypeTitle")
  public abstract RadarDto toDto(final Radar entity);

  @Mapping(target = "radarType", expression = "java(radarTypeRepository.findById(dto.getRadarTypeId()).get())")
  public abstract Radar toEntity(final RadarDto dto);

}
