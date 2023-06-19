package com.a5lab.axion.domain.radar_type;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarTypeRepository extends JpaRepository<RadarType, Long> {

  @Query("SELECT r FROM RadarType r WHERE r.code = ?1")
  Optional<RadarType> findByCode(String code);
}
