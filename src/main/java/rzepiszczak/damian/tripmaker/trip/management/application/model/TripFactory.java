package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
class TripFactory {

    private final Trips trips;
    private final HintsGenerator hintsGenerator;

    Trip create(TravelerId travelerId, String destination, LocalDate from, LocalDate to) {
        if (notExistsWithSameDestination(travelerId, destination)) {
            Trip trip = new Trip(TripId.from(UUID.randomUUID()), travelerId, Destination.of(destination), TripPeriod.from(from, to));
            trip.publishHint(hintsGenerator.generateInitialHint(trip));
            return trip;
        }
        throw new DomainException("Trying to create trip with the same destination");
    }

    private boolean notExistsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .noneMatch(trip -> trip.getDestination().getDestination().equals(destination));
    }
}
