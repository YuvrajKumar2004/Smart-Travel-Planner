package com.travelplanner.controller;

import com.travelplanner.dto.itinerary.CreateItineraryRequest;
import com.travelplanner.dto.itinerary.ItineraryResponse;
import com.travelplanner.dto.itinerary.UpdateItineraryRequest;
import com.travelplanner.service.ItineraryService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/itinerary")
@RequiredArgsConstructor
@Tag(name = "Itinerary", description = "Itinerary management APIs")
public class ItineraryController {

    private final ItineraryService itineraryService;

    @PostMapping
    @Operation(summary = "Create itinerary item")
    public ResponseEntity<ItineraryResponse> create(@Valid @RequestBody CreateItineraryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itineraryService.create(request));
    }

    @GetMapping("/{tripId}")
    @Operation(summary = "Get itinerary by trip ID")
    public ResponseEntity<List<ItineraryResponse>> getByTrip(@PathVariable Long tripId) {
        return ResponseEntity.ok(itineraryService.getByTrip(tripId));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update itinerary item")
    public ResponseEntity<ItineraryResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody UpdateItineraryRequest request) {
        return ResponseEntity.ok(itineraryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete itinerary item")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        itineraryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
