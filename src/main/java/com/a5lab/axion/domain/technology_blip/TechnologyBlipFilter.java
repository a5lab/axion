package com.a5lab.axion.domain.technology_blip;

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
public class TechnologyBlipFilter {

  @Size(min = 0, max = 64)
  private String radarTitle;

  @Size(min = 0, max = 64)
  private String ringTitle;

  @Size(min = 0, max = 64)
  private String segmentTitle;

  @Size(min = 0, max = 64)
  private String technologyTitle;

}
