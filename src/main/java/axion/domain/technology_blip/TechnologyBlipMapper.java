package axion.domain.technology_blip;

import axion.domain.ring.RingMapper;
import axion.domain.segment.SegmentMapper;
import axion.domain.technology.TechnologyMapper;
import org.mapstruct.Mapper;

import axion.config.MapperConfiguration;
import axion.utils.CycleAvoidingEntityToDtoMapper;

@Mapper(config = MapperConfiguration.class,
    uses = {SegmentMapper.class, TechnologyMapper.class, RingMapper.class})
public abstract class TechnologyBlipMapper
    extends CycleAvoidingEntityToDtoMapper<TechnologyBlip, TechnologyBlipDto> {
}
