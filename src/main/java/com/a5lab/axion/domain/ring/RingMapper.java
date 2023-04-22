package com.a5lab.axion.domain.ring;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.CycleTrackingMapper;
/*
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public interface RingMapper extends PlainMapper<Ring, RingDto> {
}
 */

@Mapper(config = MapperConfiguration.class)
public abstract class RingMapper
    extends CycleTrackingMapper<Ring, RingDto> {
}
