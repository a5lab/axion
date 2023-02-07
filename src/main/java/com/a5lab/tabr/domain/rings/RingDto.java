package com.a5lab.tabr.domain.rings;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.tabr.domain.radars.Radar;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RingDto {

  private Long id;

  @NotNull
  private Radar radar;

  @NotBlank
  @Size(min = 1, max = 64)
  @RingTitleConstraint
  private String title;

  @NotBlank
  @Size(min = 1, max = 512)
  private String description;

  @Min(0)
  @Max(512)
  private String position;

  private boolean active = true;

}
