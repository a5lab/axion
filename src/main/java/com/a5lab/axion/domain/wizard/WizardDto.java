package com.a5lab.axion.domain.wizard;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.radar_type.RadarTypeDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class WizardDto {

  @NotNull
  private RadarTypeDto radarType;

}
