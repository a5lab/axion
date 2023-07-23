package com.a5lab.axion.domain.segment;

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
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
import com.a5lab.axion.domain.radar.Radar;
import com.a5lab.axion.domain.technology_blip.TechnologyBlip;

@Entity
@Table(name = "segments")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Segment extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "radar_id", nullable = false)
  private Radar radar;

  @NotBlank
  @Size(min = 1, max = 64)
  @SegmentTrimTitleConstraint
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @Min(0)
  @Max(512)
  @Column(name = "position", nullable = false)
  private int position;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "segment", cascade = CascadeType.ALL)
  @BatchSize(size = JpaConstants.BATCH_SIZE_FOR_COLLECTIONS)
  private List<TechnologyBlip> technologyBlipList;

}
