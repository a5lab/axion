package com.a5lab.tabr.domain.blips;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.domain.entries.EntryMapper;
import com.a5lab.tabr.domain.radars.RadarMapper;
import com.a5lab.tabr.domain.rings.RingMapper;
import com.a5lab.tabr.domain.segments.SegmentMapper;
import com.a5lab.tabr.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {RadarMapper.class, SegmentMapper.class, EntryMapper.class, RingMapper.class})
public abstract class BlipMapper extends CycleAvoidingEntityToDtoMapper<Blip, BlipDto> {
}
