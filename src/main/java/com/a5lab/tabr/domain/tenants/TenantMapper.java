package com.a5lab.tabr.domain.tenants;

import com.a5lab.tabr.domain.common.CommonMapperConfig;
import com.a5lab.tabr.domain.common.EntityToRecordMapper;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfig.class)
public interface TenantMapper extends EntityToRecordMapper<Tenant, TenantRecord> {
}
