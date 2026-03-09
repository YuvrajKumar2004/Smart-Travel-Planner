package com.travelplanner.service;

import com.travelplanner.dto.trip.AddTripMemberRequest;
import com.travelplanner.dto.trip.CreateTripRequest;
import com.travelplanner.dto.trip.TripMemberResponse;
import com.travelplanner.dto.trip.TripResponse;
import com.travelplanner.exception.BadRequestException;
import com.travelplanner.exception.ResourceNotFoundException;
import com.travelplanner.model.Trip;
import com.travelplanner.model.TripMember;
import com.travelplanner.model.User;
import com.travelplanner.repository.TripMemberRepository;
import com.travelplanner.repository.TripRepository;
import com.travelplanner.repository.UserRepository;
import com.travelplanner.repository.ExpenseRepository;
import com.travelplanner.repository.ExpenseSplitRepository;
import com.travelplanner.repository.ItineraryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TripService {

    private static final String DEFAULT_MEMBER_ROLE = "MEMBER";

    private final TripRepository tripRepository;
    private final TripMemberRepository tripMemberRepository;
    private final UserRepository userRepository;
    private final ItineraryRepository itineraryRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseSplitRepository expenseSplitRepository;

    @Transactional
    public TripResponse createTrip(CreateTripRequest request) {
        validateDates(request);

        User creator = userRepository.findById(request.getCreatedBy())
            .orElseThrow(() -> new ResourceNotFoundException("Creator user not found"));

        Trip trip = Trip.builder()
            .name(request.getName().trim())
            .destination(request.getDestination().trim())
            .startDate(request.getStartDate())
            .endDate(request.getEndDate())
            .createdBy(creator)
            .build();

        Trip savedTrip = tripRepository.save(trip);

        TripMember ownerMembership = TripMember.builder()
            .trip(savedTrip)
            .user(creator)
            .role("OWNER")
            .build();
        tripMemberRepository.save(ownerMembership);

        return toTripResponse(savedTrip, List.of(ownerMembership));
    }

    @Transactional(readOnly = true)
    public TripResponse getTrip(Long id) {
        Trip trip = findTrip(id);
        List<TripMember> members = tripMemberRepository.findByTripId(id);
        return toTripResponse(trip, members);
    }

    @Transactional(readOnly = true)
    public List<TripResponse> getTripsForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User not found");
        }

        return tripMemberRepository.findByUserId(userId).stream()
            .map(TripMember::getTrip)
            .distinct()
            .map(trip -> toTripResponse(trip, tripMemberRepository.findByTripId(trip.getId())))
            .toList();
    }

    @Transactional
    public void deleteTrip(Long id) {
        Trip trip = findTrip(id);
        expenseSplitRepository.deleteByExpenseTripId(id);
        expenseRepository.deleteByTripId(id);
        itineraryRepository.deleteByTripId(id);
        tripMemberRepository.deleteByTripId(id);
        tripRepository.delete(trip);
    }

    @Transactional
    public TripResponse addMember(Long tripId, AddTripMemberRequest request) {
        Trip trip = findTrip(tripId);
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (tripMemberRepository.existsByTripIdAndUserId(tripId, user.getId())) {
            throw new BadRequestException("User is already a member of this trip");
        }

        TripMember member = TripMember.builder()
            .trip(trip)
            .user(user)
            .role(request.getRole() == null || request.getRole().isBlank() ? DEFAULT_MEMBER_ROLE : request.getRole())
            .build();
        tripMemberRepository.save(member);

        return toTripResponse(trip, tripMemberRepository.findByTripId(tripId));
    }

    private Trip findTrip(Long id) {
        return tripRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Trip not found"));
    }

    private void validateDates(CreateTripRequest request) {
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new BadRequestException("End date cannot be earlier than start date");
        }
    }

    private TripResponse toTripResponse(Trip trip, List<TripMember> members) {
        List<TripMemberResponse> memberResponses = members.stream()
            .map(m -> TripMemberResponse.builder()
                .userId(m.getUser().getId())
                .fullName(m.getUser().getFullName())
                .email(m.getUser().getEmail())
                .role(m.getRole())
                .build())
            .toList();

        return TripResponse.builder()
            .id(trip.getId())
            .name(trip.getName())
            .destination(trip.getDestination())
            .startDate(trip.getStartDate())
            .endDate(trip.getEndDate())
            .createdBy(trip.getCreatedBy().getId())
            .members(memberResponses)
            .build();
    }
}
