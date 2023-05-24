package com.a5lab.axion.domain.radar;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RadarFilter {

  @Size(min = 0, max = 64)
  private String title;

  private boolean filterByPrimary;

  private boolean filterByActive;

  private boolean primary;

  private boolean active;
}
