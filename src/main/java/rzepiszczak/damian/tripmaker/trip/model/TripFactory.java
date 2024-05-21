package rzepiszczak.damian.tripmaker.trip.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TripFactory {

    public static Trip create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = new Trip(travelerId, Destination.of(destination), Period.from(from, to));
        trip.setTripId(new TripId());
        return trip;
    }
}
