package rzepiszczak.damian.tripmaker.trip.readmodel;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TravelerTrips {

    private final List<Trip> trips = new ArrayList<>();

    void add(Trip trip) {
        trips.add(trip);
    }

    @Getter
    @Setter
    public static class Trip {
        private String tripId;
        private String destination;
        private LocalDate from;
        private LocalDate to;
    }
}
