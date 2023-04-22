package com.a5lab.axion.utils;

public interface PlainMapper<E, D> {

  D toDto(final E entity);

  E toEntity(final D dto);

}
