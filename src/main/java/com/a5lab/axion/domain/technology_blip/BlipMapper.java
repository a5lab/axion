package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.technology.EntryMapper;
import com.a5lab.axion.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {SegmentMapper.class, EntryMapper.class, RingMapper.class})
public abstract class BlipMapper extends CycleAvoidingEntityToDtoMapper<Blip, BlipDto> {
}
