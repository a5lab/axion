package com.a5lab.tabr.domain.radar_types;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.tabr.domain.AbstractAuditable;
import com.a5lab.tabr.domain.entries.Entry;
import com.a5lab.tabr.domain.radars.Radar;
import com.a5lab.tabr.domain.rings.Ring;
import com.a5lab.tabr.domain.segments.Segment;


@Entity
@Table(name = "technology_radar_types")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RadarType extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @NotBlank
  @Size(min = 1, max = 64)
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @Size(min = 0, max = 64)
  @Column(name = "code", nullable = true)
  private String website;

  @NotBlank
  @Size(min = 1, max = 512)
  @Column(name = "description", nullable = false)
  private String description;

}
