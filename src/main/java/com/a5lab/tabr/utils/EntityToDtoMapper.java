package com.a5lab.tabr.utils;

import java.util.Collection;

import org.mapstruct.IterableMapping;
import org.mapstruct.NullValueMappingStrategy;

public interface EntityToDtoMapper<E, D> {

  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
  Collection<D> toDtoCollection(final Collection<E> entityCollection);

  D toDto(final E entity);

  E toEntity(final D record);

}
