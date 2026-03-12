package com.travelplanner.controller;

import com.travelplanner.dto.trip.AddTripMemberRequest;
import com.travelplanner.dto.trip.CreateTripRequest;
import com.travelplanner.dto.trip.TripResponse;
import com.travelplanner.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Trips", description = "Trip management APIs")
public class TripController {

    private final TripService tripService;

    @PostMapping("/trips")
    @Operation(summary = "Create a trip")
    public ResponseEntity<TripResponse> createTrip(@Valid @RequestBody CreateTripRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tripService.createTrip(request));
    }

    @GetMapping("/trips/{id}")
    @Operation(summary = "Get trip details")
    public ResponseEntity<TripResponse> getTrip(@PathVariable Long id) {
        return ResponseEntity.ok(tripService.getTrip(id));
    }

    
    @GetMapping("/users/{userId}/trips")
    @Operation(summary = "Get trips for a user")
    public ResponseEntity<List<TripResponse>> getTripsForUser(@PathVariable Long userId) {
        return ResponseEntity.ok(tripService.getTripsForUser(userId));
    }


    @DeleteMapping("/trips/{id}")
    @Operation(summary = "Delete a trip")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/trips/{tripId}/members")
    @Operation(summary = "Add a member to a trip")
    public ResponseEntity<TripResponse> addTripMember(@PathVariable Long tripId,
                                                      @Valid @RequestBody AddTripMemberRequest request) {
        return ResponseEntity.ok(tripService.addMember(tripId, request));
    }
}
