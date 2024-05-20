package rzepiszczak.damian.tripmaker.planning;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
class TripService {

    private final TripRepository tripRepository;
    private final Clock clock;

    void create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = TripFactory.create(travelerId, destination, from, to);
        tripRepository.save(trip);
    }

    void assignPlan(TripId tripId, PlanDetails planDetails) {
        Optional<Trip> found = tripRepository.findById(tripId);
        found.ifPresent(trip -> trip.assign(new Timeline()));
    }

    void start(TripId tripId) {
        Optional<Trip> found = tripRepository.findById(tripId);
        found.ifPresent(trip -> trip.start(clock.now()));
    }
}
