package rzepiszczak.damian.tripmaker.trip.management.infrastructure.persistence;

import rzepiszczak.damian.tripmaker.trip.management.application.domain.TravelerId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trip;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.Trips;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class InMemoryTripRepository implements Trips {

    private final List<Trip> trips = new ArrayList<>();

    @Override
    public Optional<Trip> findById(TripId tripId) {
        return trips.stream()
                .filter(trip -> trip.getId().equals(tripId))
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
}
