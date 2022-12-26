package com.a5lab.tabr.domain;

import java.util.Collection;
import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

public interface EntityToRecordMapper<Entity, Record> {

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  Collection<Record> toDtoCollection(final Collection<Entity> entityCollection);

  Record toRecord(final Entity entity);

  Entity toEntity(final Record record);

}
