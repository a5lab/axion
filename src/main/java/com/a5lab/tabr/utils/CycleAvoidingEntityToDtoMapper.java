package com.a5lab.tabr.utils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.collections4.map.AbstractReferenceMap;
import org.apache.commons.collections4.map.ReferenceIdentityMap;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

public abstract class CycleAvoidingEntityToDtoMapper<E, D> implements EntityToDtoMapper<E, D> {

  private static final Map<Object, Object> KNOWN_INSTANCES =
      new ReferenceIdentityMap<>(
          AbstractReferenceMap.ReferenceStrength.WEAK, AbstractReferenceMap.ReferenceStrength.WEAK,
          true);
  private static final Map<Object, AtomicLong> TRACE_COUNT =
      new ReferenceIdentityMap<>(
          AbstractReferenceMap.ReferenceStrength.WEAK, AbstractReferenceMap.ReferenceStrength.WEAK,
          true);

  @BeforeMapping
  protected D getMappedInstance(E source, @TargetType Class<D> targetType) {
    return targetType.cast(KNOWN_INSTANCES.get(source));
  }

  @BeforeMapping
  protected void storeMappedInstance(E source, @MappingTarget D target) {
    KNOWN_INSTANCES.putIfAbsent(source, target);
    TRACE_COUNT.putIfAbsent(source, new AtomicLong(0));
    TRACE_COUNT.get(source).incrementAndGet();
  }

  @AfterMapping
  protected void cleanUp(E source) {
    Optional.ofNullable(TRACE_COUNT.get(source))
        .map(AtomicLong::decrementAndGet)
        .ifPresent(it -> {
          if (it <= 0) {
            KNOWN_INSTANCES.remove(source);
            TRACE_COUNT.remove(source);
          }
        });
  }

}
