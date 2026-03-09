package com.travelplanner.dto.itinerary;

import java.time.LocalTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ItineraryResponse {
    Long id;
    Long tripId;
    Integer dayNumber;
    String activity;
    String location;
    LocalTime startTime;
    LocalTime endTime;
}
