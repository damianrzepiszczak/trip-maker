package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class TripId {
    private String id;

    public static TripId from(UUID id) {
        Objects.requireNonNull(id, "trip id is required");
        return new TripId(id.toString());
    }
}
