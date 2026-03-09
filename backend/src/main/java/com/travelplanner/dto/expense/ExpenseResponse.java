package com.travelplanner.dto.expense;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExpenseResponse {
    Long id;
    Long tripId;
    Long paidBy;
    BigDecimal amount;
    String description;
    LocalDate date;
    List<ExpenseSplitResponse> splits;
}
