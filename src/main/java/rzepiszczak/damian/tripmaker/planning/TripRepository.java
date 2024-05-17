package rzepiszczak.damian.tripmaker.planning;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TripRepository {

    private final List<Trip> trips = new ArrayList<>();

    Optional<Trip> findById(TripId tripId) {
        return trips.stream()
                .filter(trip -> trip.getTripId().equals(tripId))
                .findFirst();
    }

    void save(Trip trip) {
        trips.add(trip);
    }
}
