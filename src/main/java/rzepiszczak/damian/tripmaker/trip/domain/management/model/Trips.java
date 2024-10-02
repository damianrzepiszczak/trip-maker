package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import java.util.List;
import java.util.Optional;

public interface Trips {
    Optional<Trip> findById(TripId tripId);
    List<Trip> findTravelerTrips(TravelerId travelerId);
    void save(Trip trip);
    List<Trip> findAllStarted();
}
