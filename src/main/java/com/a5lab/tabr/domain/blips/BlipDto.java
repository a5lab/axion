package com.a5lab.tabr.domain.blips;

import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.tabr.domain.entries.EntryDto;
import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.rings.RingDto;
import com.a5lab.tabr.domain.segments.SegmentDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BlipDto {

  private Long id;

  @NotNull
  private Radar radar;

  private EntryDto entry;

  private SegmentDto segment;

  // todo: remove
  // @NotNull
  // private Long segmentNo;

  private RingDto ring;

  // todo: remove
  // @NotNull
  // private Long ringNo;
}
