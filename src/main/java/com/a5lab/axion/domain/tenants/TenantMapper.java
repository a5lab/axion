package com.a5lab.axion.domain.tenants;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.EntityToDtoMapper;

@Mapper(config = MapperConfiguration.class)
public interface TenantMapper extends EntityToDtoMapper<Tenant, TenantDto> {
}
