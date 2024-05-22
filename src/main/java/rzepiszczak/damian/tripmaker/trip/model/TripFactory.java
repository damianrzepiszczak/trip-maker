package rzepiszczak.damian.tripmaker.trip.model;

import lombok.RequiredArgsConstructor;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class TripFactory {

    private final Trips trips;

    public Trip create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        if (!existsWithSameDestination(travelerId, destination)) {
            Trip trip = new Trip(travelerId, Destination.of(destination), Period.from(from, to));
            trip.setTripId(new TripId());
            return trip;
        }
        throw new IllegalStateException("Trying to create trip with the same destination");
    }

    private boolean existsWithSameDestination(TravelerId travelerId, String destination) {
        return trips.findTravelerTrips(travelerId).stream()
                .anyMatch(trip -> trip.getDestination().getDestination().equals(destination));
    }
}
