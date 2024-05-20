package rzepiszczak.damian.tripmaker.planning;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;

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

    Optional<Trip> findByTraveler(TravelerId travelerId) {
        return trips.stream()
                .filter(trip -> trip.getTravelerId().equals(travelerId))
                .findFirst();
    }

    void save(Trip trip) {
        trips.add(trip);
    }
}
