package com.a5lab.axion.domain.tenant;

import org.mapstruct.Mapper;

import com.a5lab.axion.config.MapperConfiguration;
import com.a5lab.axion.utils.PlainMapper;

@Mapper(config = MapperConfiguration.class)
public interface TenantMapper extends PlainMapper<Tenant, TenantDto> {
}
