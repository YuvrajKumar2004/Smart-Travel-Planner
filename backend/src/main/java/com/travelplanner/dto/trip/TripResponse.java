package com.travelplanner.dto.trip;

import java.time.LocalDate;
import java.util.List;
import lombok.Builder;
import lombok.Value;



@Value
@Builder
public class TripResponse 
{
    Long id;
    String name;
    String destination;
    LocalDate startDate;
    LocalDate endDate;
    Long createdBy;
    List<TripMemberResponse> members;
}
