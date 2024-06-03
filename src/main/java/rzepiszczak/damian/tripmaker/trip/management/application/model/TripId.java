package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TripId {
    private String id;

    public static TripId from(UUID id) {
        return new TripId(id.toString());
    }
}
