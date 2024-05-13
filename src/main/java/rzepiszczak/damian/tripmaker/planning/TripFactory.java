package rzepiszczak.damian.tripmaker.planning;

import java.time.LocalDateTime;

public class TripFactory {

    public static Trip create(String destination, LocalDateTime from, LocalDateTime to) {
        return new Trip(destination, from, to);
    }
}
