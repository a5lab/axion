package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.radar.RadarRepository;
import com.a5lab.axion.domain.ring.RingRepository;
import com.a5lab.axion.domain.segment.SegmentRepository;
import com.a5lab.axion.domain.technology.TechnologyRepository;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public abstract class TechnologyBlipMapper implements PlainMapper<TechnologyBlip, TechnologyBlipDto> {
  @Autowired
  protected RadarRepository radarRepository;

  @Autowired
  protected TechnologyRepository technologyRepository;

  @Autowired
  protected SegmentRepository segmentRepository;

  @Autowired
  protected RingRepository ringRepository;

  @Mapping(source = "radar.id", target = "radarId")
  @Mapping(source = "radar.title", target = "radarTitle")
  @Mapping(source = "technology.id", target = "technologyId")
  @Mapping(source = "technology.title", target = "technologyTitle")
  @Mapping(source = "technology.website", target = "technologyWebsite")
  @Mapping(source = "technology.moved", target = "technologyMoved")
  @Mapping(source = "technology.active", target = "technologyActive")
  @Mapping(source = "segment.id", target = "segmentId")
  @Mapping(source = "segment.title", target = "segmentTitle")
  @Mapping(source = "segment.position", target = "segmentPosition")
  @Mapping(source = "ring.id", target = "ringId")
  @Mapping(source = "ring.title", target = "ringTitle")
  @Mapping(source = "ring.position", target = "ringPosition")
  public abstract TechnologyBlipDto toDto(final TechnologyBlip entity);

  // fuck
  @Mapping(target = "radar", expression = "java(radarRepository.findById(dto.getRadarId()).get())")
  @Mapping(target = "technology", expression = "java(technologyRepository.findById(dto.getTechnologyId()).get())")
  @Mapping(target = "segment", expression = "java(segmentRepository.findById(dto.getSegmentId()).get())")
  @Mapping(target = "ring", expression = "java(ringRepository.findById(dto.getRingId()).get())")
  public abstract TechnologyBlip toEntity(final TechnologyBlipDto dto);
}

