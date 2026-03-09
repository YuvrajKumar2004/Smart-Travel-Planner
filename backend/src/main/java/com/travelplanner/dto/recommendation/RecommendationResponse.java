package com.travelplanner.dto.recommendation;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RecommendationResponse {
    String destination;
    List<String> attractions;
    List<String> restaurants;
    List<String> hotels;
}
