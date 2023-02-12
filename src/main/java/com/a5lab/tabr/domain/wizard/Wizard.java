package com.a5lab.tabr.domain.wizard;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.tabr.domain.AbstractAuditable;
import com.a5lab.tabr.domain.radar_types.RadarType;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wizard extends AbstractAuditable {

  @NotNull
  @JoinColumn(name = "radar_type_id", nullable = false, updatable = false)
  private RadarType radarType;
}
