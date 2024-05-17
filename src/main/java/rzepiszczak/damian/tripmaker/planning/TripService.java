package rzepiszczak.damian.tripmaker.planning;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
class TripService {

    private final TripRepository tripRepository;

    //todo: can I return tripId?
    void create(String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = TripFactory.create(destination, from, to);
        tripRepository.save(trip);
    }

    void assignPlan(TripId tripId, PlanDetails planDetails) {
        Optional<Trip> found = tripRepository.findById(tripId);
        found.ifPresent(trip -> trip.assign(new Timeline()));
    }
}
