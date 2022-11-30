package com.a5lab.tabr.ring;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicUpdate;

import java.util.Objects;

@Entity
@Table(name = "radar_rings")
@DynamicUpdate
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class Ring {

    @NotBlank
    @Id
    @Column(name = "name", unique = true, nullable = false, updatable = false)
    private String name;

    @NotBlank
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "info")
    private String info;

    @PositiveOrZero
    @Column(name = "ring_position", unique = true, nullable = false)
    private Integer position;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ring ring = (Ring) o;
        return name != null && Objects.equals(name, ring.name);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}