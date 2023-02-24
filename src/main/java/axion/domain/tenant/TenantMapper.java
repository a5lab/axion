package axion.domain.tenant;

import org.mapstruct.Mapper;

import axion.config.MapperConfiguration;
import axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface TenantMapper extends EntityToDtoMapper<Tenant, TenantDto> {
}
