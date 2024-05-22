package rzepiszczak.damian.tripmaker.trip.infrastructure;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;
import rzepiszczak.damian.tripmaker.trip.model.Trip;
import rzepiszczak.damian.tripmaker.trip.model.TripId;
import rzepiszczak.damian.tripmaker.trip.model.Trips;

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

    @Override
    public List<Trip> findTravelerTrips(TravelerId travelerId) {
        return trips.stream()
                .filter(trip -> trip.getTravelerId().getId().equals(travelerId.getId()))
                .toList();
    }

    public void save(Trip trip) {
        trips.add(trip);
    }
}
