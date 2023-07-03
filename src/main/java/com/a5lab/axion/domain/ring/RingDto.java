package com.a5lab.axion.domain.ring;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.a5lab.axion.domain.technology_blip.TechnologyBlipDto;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "radar_id", "title", "description", "position", "color", "active" })
public class RingDto {

  private Long id;

  @NotNull
  @Min(1)
  @JsonProperty("radar_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private Long radarId;

  private String radarTitle;


  @NotBlank
  @Size(min = 1, max = 64)
  @RingTitleConstraint
  private String title;

  @NotBlank
  @Size(min = 1, max = 512)
  private String description;

  @Min(0)
  @Max(512)
  private int position;

  @NotBlank
  @Size(min = 1, max = 8)
  private String color;

  private List<TechnologyBlipDto> technologyBlipDtoList;
}
