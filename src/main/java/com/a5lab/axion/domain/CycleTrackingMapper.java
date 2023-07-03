package com.a5lab.axion.domain;

import java.util.Map;

import org.apache.commons.collections4.map.AbstractReferenceMap;
import org.apache.commons.collections4.map.ReferenceIdentityMap;
import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

public abstract class CycleTrackingMapper<E, D> implements PlainMapper<E, D> {

  private static final Map<Object, Object> KNOWN_INSTANCES =
      new ReferenceIdentityMap<>(AbstractReferenceMap.ReferenceStrength.WEAK,
          AbstractReferenceMap.ReferenceStrength.WEAK, true);

  @BeforeMapping
  protected D getMappedInstance(E source, @TargetType Class<D> targetType) {
    return targetType.cast(KNOWN_INSTANCES.get(source));
  }

  @BeforeMapping
  protected void storeMappedInstance(E source, @MappingTarget D target) {
    KNOWN_INSTANCES.putIfAbsent(source, target);
  }

}
