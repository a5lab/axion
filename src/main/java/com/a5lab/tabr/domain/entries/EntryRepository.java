package com.a5lab.tabr.domain.entries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntryRepository extends JpaRepository<Entry, Long> {
  Optional<Entry> findByTitle(String title);
}
