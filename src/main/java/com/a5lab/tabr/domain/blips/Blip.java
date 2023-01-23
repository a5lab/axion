package com.a5lab.tabr.domain.blips;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.tabr.domain.AbstractAuditable;

@Entity
@Table(name = "blips")
@DynamicUpdate
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Blip extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  private Long id;

  @Column(name = "radar_id", nullable = false, updatable = false)
  private Long radarId;

  @Column(name = "entry_id", nullable = false, updatable = false)
  private Long entryId;
  
  @Column(name = "segment_id", nullable = false, updatable = false)
  private Long segmentId;

  @NotNull
  @Column(name = "segment_no", nullable = false, updatable = false)
  private Long segmentNo;

  @Column(name = "ring_id", nullable = false, updatable = false)
  private Long ringId;

  @NotNull
  @Column(name = "ring_no", nullable = false, updatable = false)
  private Long ringNo;

}
