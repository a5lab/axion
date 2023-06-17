package com.a5lab.axion.domain.wizard;

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
public class WizardDto {

  @NotNull
  private long radarTypeId;

  private String radarTypeCode;

  private String radarTypeTitle;
}
