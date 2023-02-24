package axion.domain.ring;

import org.mapstruct.Mapper;

import axion.config.MapperConfiguration;
import axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface RingMapper extends EntityToDtoMapper<Ring, RingDto> {
}
