package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
class TripFactory {

    private final Trips trips;

    Trip create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        if (notExistsWithSameDestination(travelerId, destination)) {
            return new Trip(TripId.from(UUID.randomUUID()), travelerId, Destination.of(destination), Period.from(from, to));
        }
        throw new IllegalStateException("Trying to create trip with the same destination");
    }

    private boolean notExistsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .noneMatch(trip -> trip.getDestination().getDestination().equals(destination));
    }
}
