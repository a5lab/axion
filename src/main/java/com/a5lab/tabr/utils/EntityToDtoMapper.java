package com.a5lab.tabr.utils;

import java.util.Collection;

import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

public interface EntityToDtoMapper<E, R> {

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  Collection<R> toDtoCollection(final Collection<E> entityCollection);

  R toDto(final E entity);

  E toEntity(final R record);

}
