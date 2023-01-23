package com.a5lab.tabr.domain.blips;

import jakarta.validation.constraints.NotNull;

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

  @NotNull
  private Long segmentNo;

  private Long ringId;

  @NotNull
  private Long ringNo;
}
