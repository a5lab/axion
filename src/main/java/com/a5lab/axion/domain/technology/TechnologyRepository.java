package com.a5lab.axion.domain.technology;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long>,
    JpaSpecificationExecutor<Technology> {
  Optional<Technology> findByTitle(String title);
}
