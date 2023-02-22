package com.a5lab.axion.domain.ring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.axion.domain.AbstractAuditable;
import com.a5lab.axion.domain.radar.Radar;


@Entity
@Table(name = "rings")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Ring extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "radar_id", nullable = false, updatable = false)
  private Radar radar;

  @NotBlank
  @RingTitleConstraint
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @Min(0)
  @Max(512)
  @Column(name = "position", nullable = false)
  private int position;

  @Column(name = "is_active", nullable = false)
  private boolean active = true;

}