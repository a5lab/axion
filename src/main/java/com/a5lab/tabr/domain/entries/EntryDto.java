package com.a5lab.tabr.domain.entries;

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
public class EntryDto {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  @Size(min = 0, max = 64)
  private String website;

  @NotBlank
  @Size(min = 1, max = 512)
  private String description;

  private int moved;

  private boolean active;

}
