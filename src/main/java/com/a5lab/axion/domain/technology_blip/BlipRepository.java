package com.a5lab.axion.domain.technology_blip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlipRepository extends JpaRepository<Blip, Long> {
}
