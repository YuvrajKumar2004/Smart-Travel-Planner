package com.travelplanner.dto.expense;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserBalanceResponse {
    Long userId;
    BigDecimal balance;
}
