package com.a5lab.axion.domain.radar_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarTypeRepository extends JpaRepository<RadarType, Long> {
}
