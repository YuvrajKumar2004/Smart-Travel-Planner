package com.travelplanner.repository;

import com.travelplanner.model.ExpenseSplit;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseSplitRepository extends JpaRepository<ExpenseSplit, Long> {
    List<ExpenseSplit> findByExpenseId(Long expenseId);

    List<ExpenseSplit> findByExpenseTripId(Long tripId);

    void deleteByExpenseTripId(Long tripId);
}
