package com.a5lab.tabr.domain.blips;

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
public class BlipDto {

  private Long id;

  private Long radarId;

  private Long entryId;

  private Long segmentId;

  private Long segmentNo;

  private Long ringId;

  private Long ringNo;
}
