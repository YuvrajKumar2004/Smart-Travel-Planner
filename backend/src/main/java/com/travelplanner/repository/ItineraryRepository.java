package com.travelplanner.repository;

import com.travelplanner.model.ItineraryItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<ItineraryItem, Long> {
    List<ItineraryItem> findByTripIdOrderByDayNumberAscStartTimeAsc(Long tripId);

    void deleteByTripId(Long tripId);
}
