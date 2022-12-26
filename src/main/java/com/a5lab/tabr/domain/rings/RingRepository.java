package com.a5lab.tabr.domain.rings;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RingRepository extends JpaRepository<Ring, Long> {
  Optional<Ring> findRingByTitle(String title);

}
