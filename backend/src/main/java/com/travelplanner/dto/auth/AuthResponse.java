package com.travelplanner.dto.auth;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthResponse {
    Long userId;
    String fullName;
    String email;
    String token;
    String tokenType;
}
