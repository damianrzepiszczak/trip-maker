package rzepiszczak.damian.tripmaker.trip;

import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.util.Optional;

interface Trips {
    Optional<Trip> findById(TripId tripId);
    Optional<Trip> findByTraveler(TravelerId travelerId);
    void save(Trip trip);
}