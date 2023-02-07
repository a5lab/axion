package com.a5lab.tabr.domain.radar_types;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarTypeRepository extends JpaRepository<RadarType, Long> {
}
