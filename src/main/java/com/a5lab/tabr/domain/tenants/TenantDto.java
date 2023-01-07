package com.a5lab.tabr.domain.tenants;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TenantDto {

  @NotBlank
  private String title;

  @NotBlank
  private String description;

  public TenantDto() {
    // TODO: remove after add default null for next contructor
  }

  public TenantDto(String title, String description) {
    // TODO: where is init list?
    // TODO: how to make default value
    this.title = title;
    this.description = description;
  }
}
