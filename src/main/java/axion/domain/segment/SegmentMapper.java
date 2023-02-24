package axion.domain.segment;

import org.mapstruct.Mapper;

import axion.config.MapperConfiguration;
import axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface SegmentMapper extends EntityToDtoMapper<Segment, SegmentDto> {
}
