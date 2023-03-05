package com.a5lab.axion.domain.ring;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface RingRepository extends JpaRepository<Ring, Long>,
    JpaSpecificationExecutor<Ring> {
  Optional<Ring> findByTitle(String title);
}
