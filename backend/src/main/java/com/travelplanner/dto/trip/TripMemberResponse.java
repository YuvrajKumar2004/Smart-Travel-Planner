package com.travelplanner.dto.trip;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class TripMemberResponse {
    Long userId;
    String fullName;
    String email;
    String role;
}
