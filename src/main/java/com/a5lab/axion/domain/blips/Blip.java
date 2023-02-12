package com.a5lab.axion.domain.blips;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.axion.domain.AbstractAuditable;
import com.a5lab.axion.domain.entries.Entry;
import com.a5lab.axion.domain.radars.Radar;
import com.a5lab.axion.domain.rings.Ring;
import com.a5lab.axion.domain.segments.Segment;


@Entity
@Table(name = "technology_blips")
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

  @NotNull
  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "radar_id", nullable = false, updatable = false)
  private Radar radar;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "technology_id", nullable = false, updatable = false)
  private Entry entry;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "segment_id", nullable = false, updatable = false)
  private Segment segment;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "ring_id", nullable = false, updatable = false)
  private Ring ring;
}
