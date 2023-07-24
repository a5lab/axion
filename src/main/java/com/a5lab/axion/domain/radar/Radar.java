package com.a5lab.axion.domain.radar;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.axion.domain.AbstractAuditable;
import com.a5lab.axion.domain.JpaConstants;
import com.a5lab.axion.domain.radar_type.RadarType;
import com.a5lab.axion.domain.ring.Ring;
import com.a5lab.axion.domain.segment.Segment;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;

@Entity
@Table(name = "radars")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Radar extends AbstractAuditable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "radar_type_id", nullable = false)
  private RadarType radarType;

  @NotBlank
  @Size(min = 1, max = 64)
  @RadarTrimTitleConstraint
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "is_primary", nullable = false)
  private boolean primary = true;

  @Column(name = "is_active", nullable = false)
  private boolean active = true;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "radar", cascade = CascadeType.ALL)
  @BatchSize(size = JpaConstants.BATCH_SIZE_FOR_COLLECTIONS)
  private List<Ring> ringList;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "radar", cascade = CascadeType.ALL)
  @BatchSize(size = JpaConstants.BATCH_SIZE_FOR_COLLECTIONS)
  private List<Segment> segmentList;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "radar", cascade = CascadeType.ALL)
  @BatchSize(size = JpaConstants.BATCH_SIZE_FOR_COLLECTIONS)
  private List<TechnologyBlip> technologyBlipList;
}
