package com.a5lab.axion.domain.technology_blip;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyBlipRepository extends JpaRepository<TechnologyBlip, Long> {
  @Query("SELECT t FROM TechnologyBlip t WHERE t.radar.id = ?1 AND t.technology.id = ?2")
  List<TechnologyBlip> findByRadarIdAndTechnologyId(Long radarId, Long technologyId);
}
