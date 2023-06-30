package com.a5lab.axion.domain.segment;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipMapper;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {TechnologyBlipMapper.class})
public abstract class SegmentMapper implements PlainMapper<Segment, SegmentDto> {
  @Autowired
  protected RadarRepository radarRepository;

  @Mapping(source = "radar.id", target = "radarId")
  @Mapping(source = "radar.title", target = "radarTitle")
  @Mapping(source = "technologyBlipList", target = "technologyBlipDtoList")
  public abstract SegmentDto toDto(final Segment entity);

  @Mapping(target = "radar", expression = "java(getRadar(dto))")
  @Mapping(source = "technologyBlipDtoList", target = "technologyBlipList")
  public abstract Segment toEntity(final SegmentDto dto);

  Radar getRadar(SegmentDto segmentDto) {
    if(segmentDto.getRadarId() != null){
      return radarRepository.findById(segmentDto.getRadarId()).get();
    }
    return null;
  }
}
