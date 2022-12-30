package com.a5lab.tabr.domain;

import java.util.Collection;

import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

public interface EntityToRecordMapper<E, R> {

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  Collection<R> toDtoCollection(final Collection<E> entityCollection);

  R toRecord(final E entity);

  E toEntity(final R record);

}
