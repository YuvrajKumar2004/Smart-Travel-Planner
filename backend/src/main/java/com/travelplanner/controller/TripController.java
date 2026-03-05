package com.travelplanner.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trips")
public class TripController {

    @PostMapping
    public String createTrip() {
        return "create trip endpoint";
    }

    @GetMapping("/{id}")
    public String getTrip(@PathVariable Long id) {
        return "trip " + id;
    }
}
