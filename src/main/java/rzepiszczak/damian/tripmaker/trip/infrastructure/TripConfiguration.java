package rzepiszczak.damian.tripmaker.trip.infrastructure;

import rzepiszczak.damian.tripmaker.trip.model.Trips;

public class TripConfiguration {

    public Trips tripRepository() {
        return new InMemoryTripRepository();
    }
}
