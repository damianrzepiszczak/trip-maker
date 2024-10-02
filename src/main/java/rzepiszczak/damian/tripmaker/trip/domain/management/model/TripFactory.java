package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.common.exception.DomainException;

import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class TripFactory {

    private final Trips trips;
    private final TripsSettings tripsSettings;

    public Trip create(TravelerId travelerId, String destination, LocalDate from, LocalDate to) {
        if (notExistsWithSameDestination(travelerId, destination) && isNumberOfAllowedTripsNotExceeded(travelerId)) {
            return new Trip(TripId.from(UUID.randomUUID()), travelerId, Destination.of(destination), TripPeriod.from(from, to));
        }
        throw new DomainException("Trying to create trip with the same destination");
    }

    private boolean notExistsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .noneMatch(trip -> trip.getDestination().getName().equals(destination));
    }

    private boolean isNumberOfAllowedTripsNotExceeded(TravelerId travelerId) {
        return trips.findTravelerTrips(travelerId).size() < tripsSettings.allowedNumberOfTrips();
    }
}
