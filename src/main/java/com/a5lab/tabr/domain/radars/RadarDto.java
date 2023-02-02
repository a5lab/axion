package com.a5lab.tabr.domain.radars;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.tabr.domain.blips.BlipDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@RadarDtoPrimaryConstraint
public class RadarDto {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  @Size(min = 1, max = 512)
  private String description;

  private boolean primary;

  private List<BlipDto> blipList;
}
