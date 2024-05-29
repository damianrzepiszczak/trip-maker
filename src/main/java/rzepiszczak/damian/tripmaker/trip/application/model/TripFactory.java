package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
class TripFactory {

    private final Trips trips;

    Trip create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        if (notExistsWithSameDestination(travelerId, destination)) {
            Trip trip = new Trip(travelerId, Destination.of(destination), Period.from(from, to));
            trip.setTripId(TripId.from(UUID.randomUUID()));
            return trip;
        }
        throw new IllegalStateException("Trying to create trip with the same destination");
    }

    private boolean notExistsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .noneMatch(trip -> trip.getDestination().getDestination().equals(destination));
    }
}
