package com.a5lab.tabr.domain.blips;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.tabr.domain.entries.EntryDto;
import com.a5lab.tabr.domain.radars.RadarDto;
import com.a5lab.tabr.domain.rings.RingDto;
import com.a5lab.tabr.domain.segments.SegmentDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlipDto {

  private Long id;

  private RadarDto radar;

  private EntryDto entry;

  private SegmentDto segment;

  @NotNull
  private Long segmentNo;

  private RingDto ring;

  @NotNull
  private Long ringNo;
}
