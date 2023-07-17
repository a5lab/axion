package com.a5lab.axion.domain.tenant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class should not have any validation such as @NotNull etc
 * due to custom primary validation at service layer.
 */

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TenantDto {

  private Long id;

  private String title;

  private String description;

}
