package com.travelplanner.dto.expense;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TripExpenseSummaryResponse {
    Long tripId;
    List<ExpenseResponse> expenses;
    List<UserBalanceResponse> balances;
}
