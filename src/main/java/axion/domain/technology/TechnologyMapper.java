package axion.domain.technology;

import org.mapstruct.Mapper;

import axion.config.MapperConfiguration;
import axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface TechnologyMapper extends EntityToDtoMapper<Technology, TechnologyDto> {
}
