package com.a5lab.axion.domain;

public interface PlainMapper<E, D> {

  D toDto(final E entity);

  E toEntity(final D dto);

}
