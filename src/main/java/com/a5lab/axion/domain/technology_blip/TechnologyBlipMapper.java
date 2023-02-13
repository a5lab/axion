package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {SegmentMapper.class, TechnologyMapper.class, RingMapper.class})
public abstract class TechnologyBlipMapper extends CycleAvoidingEntityToDtoMapper<TechnologyBlip, TechnologyBlipDto> {
}
