package rzepiszczak.damian.tripmaker.trip.infrastructure;

import rzepiszczak.damian.tripmaker.trip.application.model.Trips;

public class TripConfiguration {

    public Trips tripRepository() {
        return new InMemoryTripRepository();
    }
}
