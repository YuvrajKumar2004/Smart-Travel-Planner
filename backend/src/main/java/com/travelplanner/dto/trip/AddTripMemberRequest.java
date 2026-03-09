package com.travelplanner.dto.trip;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddTripMemberRequest {

    @NotNull
    private Long userId;

    private String role;
}
