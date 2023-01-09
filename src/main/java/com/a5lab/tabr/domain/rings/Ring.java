package com.a5lab.tabr.domain.rings;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicUpdate;

import com.a5lab.tabr.domain.AbstractAuditable;

@Entity
@Table(name = "rings")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Ring extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @Setter(AccessLevel.NONE)
  private Long id;

  @NotBlank
  @RingTitleConstraint
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "info")
  private String info;

  @Column(name = "priority", nullable = false)
  private int priority;

  @Column(name = "is_active", nullable = false)
  private boolean active = true;

}