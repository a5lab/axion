package com.a5lab.axion.domain.radar_type;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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


@Entity
@Table(name = "radar_types")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RadarType extends AbstractAuditable {

  public static final String CAPABILITY_RADAR = "capability";
  public static final String PRACTICE_RADAR = "practice";
  public static final String PROCESS_RADAR = "process";
  public static final String TECHNOLOGY_RADAR = "technology";


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
  private String code;

  @NotBlank
  @Size(min = 1, max = 512)
  @Column(name = "description", nullable = false)
  private String description;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "radarType", cascade = CascadeType.ALL)
  @BatchSize(size = JpaConstants.BATCH_SIZE_FOR_COLLECTIONS)
  private List<Radar> radarList;
}
