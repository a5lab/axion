package com.a5lab.axion.domain.radar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RadarDto {

  private Long id;

  @NotNull
  private RadarType radarType;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  private String description;

  private boolean primary;

  private boolean active = true;

  private List<RingDto> ringList;

  private List<SegmentDto> segmentList;

  private List<TechnologyBlipDto> technologyBlipList;
}
