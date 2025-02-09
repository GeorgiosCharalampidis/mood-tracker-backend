package com.moodtracker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ThoughtRepository extends JpaRepository<Thought, Long> {
    // Find all thoughts by a specific user
    List<Thought> findByUser(User user);

    // Find thoughts by user and date range
    List<Thought> findByUserAndDateBetween(User user, LocalDate startDate, LocalDate endDate);
}