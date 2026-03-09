package com.travelplanner.service;

import com.travelplanner.dto.itinerary.CreateItineraryRequest;
import com.travelplanner.dto.itinerary.ItineraryResponse;
import com.travelplanner.dto.itinerary.UpdateItineraryRequest;
import com.travelplanner.exception.BadRequestException;
import com.travelplanner.exception.ResourceNotFoundException;
import com.travelplanner.model.ItineraryItem;
import com.travelplanner.model.Trip;
import com.travelplanner.repository.ItineraryRepository;
import com.travelplanner.repository.TripRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItineraryService {

    private final ItineraryRepository itineraryRepository;
    private final TripRepository tripRepository;

    @Transactional
    public ItineraryResponse create(CreateItineraryRequest request) {
        validateTime(request.getStartTime().toSecondOfDay(), request.getEndTime().toSecondOfDay());

        Trip trip = tripRepository.findById(request.getTripId())
            .orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

        ItineraryItem item = ItineraryItem.builder()
            .trip(trip)
            .dayNumber(request.getDayNumber())
            .activity(request.getActivity().trim())
            .location(request.getLocation().trim())
            .startTime(request.getStartTime())
            .endTime(request.getEndTime())
            .build();

        return toResponse(itineraryRepository.save(item));
    }

    @Transactional(readOnly = true)
    public List<ItineraryResponse> getByTrip(Long tripId) {
        if (!tripRepository.existsById(tripId)) {
            throw new ResourceNotFoundException("Trip not found");
        }
        return itineraryRepository.findByTripIdOrderByDayNumberAscStartTimeAsc(tripId).stream()
            .map(this::toResponse)
            .toList();
    }

    @Transactional
    public ItineraryResponse update(Long id, UpdateItineraryRequest request) {
        validateTime(request.getStartTime().toSecondOfDay(), request.getEndTime().toSecondOfDay());

        ItineraryItem item = itineraryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Itinerary item not found"));

        item.setDayNumber(request.getDayNumber());
        item.setActivity(request.getActivity().trim());
        item.setLocation(request.getLocation().trim());
        item.setStartTime(request.getStartTime());
        item.setEndTime(request.getEndTime());

        return toResponse(itineraryRepository.save(item));
    }

    @Transactional
    public void delete(Long id) {
        ItineraryItem item = itineraryRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Itinerary item not found"));
        itineraryRepository.delete(item);
    }

    private void validateTime(int startSecond, int endSecond) {
        if (endSecond <= startSecond) {
            throw new BadRequestException("End time must be later than start time");
        }
    }

    private ItineraryResponse toResponse(ItineraryItem item) {
        return ItineraryResponse.builder()
            .id(item.getId())
            .tripId(item.getTrip().getId())
            .dayNumber(item.getDayNumber())
            .activity(item.getActivity())
            .location(item.getLocation())
            .startTime(item.getStartTime())
            .endTime(item.getEndTime())
            .build();
    }
}
