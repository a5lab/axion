package com.a5lab.axion.domain.radar;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RadarRepository extends JpaRepository<Radar, Long>,
    JpaSpecificationExecutor<Radar> {
  @Query("SELECT r FROM Radar r WHERE r.primary = ?1")
  List<Radar> findByPrimary(boolean primary);

  @Query("SELECT r FROM Radar r WHERE r.primary = ?1 AND r.active = ?2")
  List<Radar> findByPrimaryAndActive(boolean primary, boolean active);
}
