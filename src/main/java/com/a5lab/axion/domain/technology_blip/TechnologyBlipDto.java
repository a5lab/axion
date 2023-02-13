package com.a5lab.axion.domain.technology_blip;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.ring.RingDto;
import com.a5lab.axion.domain.segment.SegmentDto;
import com.a5lab.axion.domain.technology.TechnologyDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TechnologyBlipDto {

  private Long id;

  @NotNull
  private Radar radar;

  private TechnologyDto entry;

  private SegmentDto segment;

  private RingDto ring;
}
