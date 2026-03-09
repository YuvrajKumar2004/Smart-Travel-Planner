package com.travelplanner.dto.expense;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ExpenseSplitResponse {
    Long userId;
    BigDecimal shareAmount;
}
