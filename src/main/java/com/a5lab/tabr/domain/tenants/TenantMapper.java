package com.a5lab.tabr.domain.tenants;

import org.mapstruct.Mapper;

import com.a5lab.tabr.config.CommonMapperConfig;
import com.a5lab.tabr.utils.EntityToRecordMapper;

@Mapper(config = CommonMapperConfig.class)
public interface TenantMapper extends EntityToRecordMapper<Tenant, TenantRecord> {
}
