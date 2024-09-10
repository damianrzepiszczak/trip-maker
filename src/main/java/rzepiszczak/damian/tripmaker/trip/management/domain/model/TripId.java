package rzepiszczak.damian.tripmaker.trip.management.domain.model;

import java.util.Objects;
import java.util.UUID;

public record TripId(String id) {

    public static TripId from(UUID id) {
        Objects.requireNonNull(id, "trip id is required");
        return new TripId(id.toString());
    }

    public static TripId from(String id) {
        Objects.requireNonNull(id, "trip id is required");
        return new TripId(id);
    }
}
