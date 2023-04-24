package com.a5lab.axion.domain.radar;

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

import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;

@JsonPropertyOrder({"id", "radar_type_id", "title", "description", "primary", "active"})
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RadarDto {

  private Long id;

  @NotNull
  @JsonProperty("radar_type_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private RadarType radarType;

  @NotBlank
  @Size(min = 1, max = 64)
  private String title;

  @NotBlank
  private String description;

  private boolean primary;

  private boolean active = true;

  private List<Ring> ringList;

  private List<Segment> segmentList;

  private List<TechnologyBlip> technologyBlipList;
}
