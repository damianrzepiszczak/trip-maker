package rzepiszczak.damian.tripmaker.trip.infrastructure.persistence;

import rzepiszczak.damian.tripmaker.trip.domain.management.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class InMemoryTripRepository implements Trips {

    private final List<Trip> trips = new ArrayList<>();

    @Override
    public Optional<Trip> findById(TripId tripId) {
        return trips.stream()
                .filter(trip -> trip.getId().id().equals(tripId.id()))
                .findFirst();
    }

    @Override
    public List<Trip> findTravelerTrips(TravelerId travelerId) {
        return trips.stream()
                .filter(trip -> trip.getTravelerId().getId().equals(travelerId.getId()))
                .toList();
    }

    @Override
    public void save(Trip trip) {
        trips.add(trip);
    }

    @Override
    public List<Trip> findAllStarted() {
        return trips.stream()
                .filter(trip -> trip.getStatus() == TripStatus.STARTED)
                .toList();
    }
}
