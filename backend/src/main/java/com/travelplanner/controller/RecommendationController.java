package com.travelplanner.controller;

import com.travelplanner.dto.recommendation.RecommendationResponse;
import com.travelplanner.service.RecommendationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recommendations")
@RequiredArgsConstructor
@Tag(name = "Recommendations", description = "Destination recommendation APIs")
public class RecommendationController {

    private final RecommendationService recommendationService;

    @GetMapping
    @Operation(summary = "Get destination recommendations")
    public ResponseEntity<RecommendationResponse> getRecommendations(@RequestParam String destination) {
        return ResponseEntity.ok(recommendationService.getRecommendations(destination));
    }
}
