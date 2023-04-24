package com.a5lab.axion.domain.technology_blip;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

import com.a5lab.axion.domain.ring.RingMapper;
import com.a5lab.axion.domain.segment.SegmentMapper;
import com.a5lab.axion.domain.technology.TechnologyMapper;
import com.a5lab.axion.utils.CycleTrackingMapper;

@Mapper(componentModel = "spring", uses = {RingMapper.class, SegmentMapper.class,
    TechnologyMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class TechnologyBlipMapper
    extends CycleTrackingMapper<TechnologyBlip, TechnologyBlipDto> {
}
