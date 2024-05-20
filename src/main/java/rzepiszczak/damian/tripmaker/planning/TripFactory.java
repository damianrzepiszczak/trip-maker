package rzepiszczak.damian.tripmaker.planning;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import rzepiszczak.damian.tripmaker.traveler.TravelerId;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TripFactory {

    public static Trip create(TravelerId travelerId, String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = new Trip(travelerId, Destination.of(destination), from, to);
        trip.setTripId(new TripId());
        return trip;
    }
}
