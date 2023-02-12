package com.a5lab.axion.domain.blips;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.domain.entries.EntryMapper;
import com.a5lab.axion.domain.rings.RingMapper;
import com.a5lab.axion.domain.segments.SegmentMapper;
import com.a5lab.axion.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {SegmentMapper.class, EntryMapper.class, RingMapper.class})
public abstract class BlipMapper extends CycleAvoidingEntityToDtoMapper<Blip, BlipDto> {
}
