package com.a5lab.tabr.domain.segments;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegmentRepository extends JpaRepository<Segment, Long> {
  Optional<Segment> findByTitle(String title);
}
