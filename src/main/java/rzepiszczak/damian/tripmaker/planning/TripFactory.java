package rzepiszczak.damian.tripmaker.planning;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TripFactory {

    public static Trip create(String destination, LocalDateTime from, LocalDateTime to) {
        Trip trip = new Trip(destination, from, to);
        trip.setTripId(new TripId());
        return trip;
    }
}
