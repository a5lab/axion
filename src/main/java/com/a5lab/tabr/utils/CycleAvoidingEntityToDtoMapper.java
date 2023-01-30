package com.a5lab.tabr.utils;

import java.util.IdentityHashMap;
import java.util.Map;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

public interface CycleAvoidingEntityToDtoMapper<E, D> extends EntityToDtoMapper<E, D> {

  Map<Object, Object> knownInstances = new IdentityHashMap<>();

  @BeforeMapping
  default <D> D getMappedInstance(E source, @TargetType Class<D> targetType) {
    return (D) knownInstances.get(source);
  }

  @BeforeMapping
  default void storeMappedInstance(E source, @MappingTarget D target) {
    knownInstances.put(source, target);
  }

}
