package com.travelplanner.dto.itinerary;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;
import lombok.Data;

@Data
public class UpdateItineraryRequest {

    @NotNull
    @Min(1)
    @Max(31)
    private Integer dayNumber;

    @NotBlank
    private String activity;

    @NotBlank
    private String location;

    @NotNull
    private LocalTime startTime;

    @NotNull
    private LocalTime endTime;
}
