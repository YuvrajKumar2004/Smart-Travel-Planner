package com.travelplanner.repository;

import com.travelplanner.model.Expense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByTripIdOrderByDateDescIdDesc(Long tripId);

    void deleteByTripId(Long tripId);
}
