package rzepiszczak.damian.tripmaker.trip;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class InMemoryTripRepository implements Trips {

    private final List<Trip> trips = new ArrayList<>();

    public Optional<Trip> findById(TripId tripId) {
        return trips.stream()
                .filter(trip -> trip.getTripId().equals(tripId))
                .findFirst();
    }

    public Optional<Trip> findByTraveler(TravelerId travelerId) {
        return trips.stream()
                .filter(trip -> trip.getTravelerId().equals(travelerId))
                .findFirst();
    }

    public void save(Trip trip) {
        trips.add(trip);
    }
}
