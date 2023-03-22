package com.a5lab.axion.domain.segment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SegmentDto {

  private Long id;

  @NotNull
  private Radar radar;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  private String description;

  @Min(0)
  @Max(512)
  private int position;

  private boolean active = true;

  private List<TechnologyBlip> technologyBlipList;

}
