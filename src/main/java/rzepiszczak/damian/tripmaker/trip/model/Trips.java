package rzepiszczak.damian.tripmaker.trip.model;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.util.Optional;

public interface Trips {
    Optional<Trip> findById(TripId tripId);
    Optional<Trip> findByTraveler(TravelerId travelerId);
    void save(Trip trip);
}
