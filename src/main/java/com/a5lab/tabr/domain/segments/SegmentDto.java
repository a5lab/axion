package com.a5lab.tabr.domain.segments;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

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
public class SegmentDto {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  private String description;

  @NotBlank
  @Size(min = 1, max = 512)
  private String position;


  private boolean active = true;
}
