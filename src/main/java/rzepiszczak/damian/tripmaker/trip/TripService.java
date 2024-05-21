package rzepiszczak.damian.tripmaker.trip;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.Clock;
import rzepiszczak.damian.tripmaker.planning.PlanDetails;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
class TripService {

    private final Trips trips;
    private final Clock clock;

    void create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = TripFactory.create(travelerId, destination, from, to);
        trips.save(trip);
    }

    void assignPlan(TripId tripId, PlanDetails planDetails) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> trip.assign(new Timeline()));
    }

    void start(TripId tripId) {
        Optional<Trip> found = trips.findById(tripId);
        found.ifPresent(trip -> trip.start(clock.now()));
    }
}
