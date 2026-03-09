package com.travelplanner.repository;

import com.travelplanner.model.Trip;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
    List<Trip> findByCreatedById(Long createdById);
}
