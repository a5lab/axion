package com.a5lab.tabr.domain.tenants;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.MapperConfiguration;
import com.a5lab.tabr.utils.EntityToRecordMapper;

@Mapper(config = MapperConfiguration.class)
public interface TenantMapper extends EntityToRecordMapper<Tenant, TenantRecord> {
}
