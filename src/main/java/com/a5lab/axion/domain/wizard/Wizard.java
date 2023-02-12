package com.a5lab.axion.domain.wizard;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.AbstractAuditable;
import com.a5lab.axion.domain.radar_type.RadarType;

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
