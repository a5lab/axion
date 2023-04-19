package com.a5lab.axion.domain.radar_type;

import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RadarTypeDto {

  private Long id;

  @NotBlank
  private String title;

  @NotBlank
  private String code;

  private String description;

  // private List<RadarDto> radarList;
}
