package com.a5lab.tabr.domain.rings;

import com.a5lab.tabr.domain.AbstractAuditable;
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

@Entity
@Table(name = "rings")
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Radar extends AbstractAuditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false, updatable = false, unique = true)
  @Setter(AccessLevel.NONE)
  private Integer id;

  @NotBlank
  @RingTitleConstraint
  @Column(name = "title", unique = true, nullable = false)
  private String title;

  @NotBlank
  @Column(name = "description", nullable = false)
  private String description;

  @NotBlank
  @Column(name = "width", nullable = false)
  private int width;

  @NotBlank
  @Column(name = "height", nullable = false)
  private int height;

  @NotBlank
  @Column(name = "background", nullable = false)
  private String background;

  @NotBlank
  @Column(name = "grid", nullable = false)
  private String grid;

  @NotBlank
  @Column(name = "inactive", nullable = false)
  private String inactive;
}