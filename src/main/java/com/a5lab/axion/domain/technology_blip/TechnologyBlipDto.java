package com.a5lab.axion.domain.technology_blip;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

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

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({"id", "radar_id", "technology_id", "segment_id", "ring_id"})
public class TechnologyBlipDto {

  private Long id;

  @NotNull
  @Min(1)
  @JsonProperty("radar_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private Long radarId;
  private String radarTitle;

  @NotNull
  @Min(1)
  @JsonProperty("technology_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private long technologyId;
  private String technologyTitle;
  private String technologyWebsite;
  private int technologyMoved;
  private boolean technologyActive;

  @NotNull
  @Min(1)
  @JsonProperty("segment_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private long segmentId;
  private String segmentTitle;
  private int segmentPosition;

  @NotNull
  @Min(1)
  @JsonProperty("ring_id")
  @JsonIdentityReference(alwaysAsId = true)
  @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
  private long ringId;
  private String ringTitle;
  private int ringPosition;

}
