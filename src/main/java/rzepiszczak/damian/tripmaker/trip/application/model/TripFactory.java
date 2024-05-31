package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
class TripFactory {

    private final Trips trips;

    Trip create(TravelerId travelerId, String destination, LocalDate from, LocalDate to) {
        if (notExistsWithSameDestination(travelerId, destination)) {
            return new Trip(TripId.from(UUID.randomUUID()), travelerId, Destination.of(destination), Period.from(from, to));
        }
        throw new DomainException("Trying to create trip with the same destination");
    }

    private boolean notExistsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .noneMatch(trip -> trip.getDestination().getDestination().equals(destination));
    }
}
