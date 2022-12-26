package com.a5lab.tabr.domain.tenants;

import com.a5lab.tabr.domain.CommonMapperConfig;
import com.a5lab.tabr.domain.EntityToRecordMapper;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface TenantMapper extends EntityToRecordMapper<Tenant, TenantRecord> {
}
