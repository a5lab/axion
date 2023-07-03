package com.a5lab.axion.domain.radar;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.PlainMapper;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.radar_type.RadarTypeRepository;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {RingMapper.class, SegmentMapper.class, TechnologyBlipMapper.class})
public abstract class RadarMapper implements PlainMapper<Radar, RadarDto> {

  @Autowired
  protected RadarTypeRepository radarTypeRepository;

  @Mapping(source = "radarType.id", target = "radarTypeId")
  @Mapping(source = "radarType.title", target = "radarTypeTitle")
  @Mapping(source = "ringList", target = "ringDtoList")
  @Mapping(source = "segmentList", target = "segmentDtoList")
  @Mapping(source = "technologyBlipList", target = "technologyBlipDtoList")
  public abstract RadarDto toDto(final Radar entity);

  @Mapping(target = "radarType", expression = "java(getRadarType(dto))")
  @Mapping(source = "ringDtoList", target = "ringList")
  @Mapping(source = "segmentDtoList", target = "segmentList")
  @Mapping(source = "technologyBlipDtoList", target = "technologyBlipList")
  public abstract Radar toEntity(final RadarDto dto);

  RadarType getRadarType(RadarDto radarDto) {
    if (radarDto.getRadarTypeId() != null) {
      return radarTypeRepository.findById(radarDto.getRadarTypeId()).get();
    }
    return null;
  }
}
