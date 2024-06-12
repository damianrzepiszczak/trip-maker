package rzepiszczak.damian.tripmaker.trip.management.application.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class TripId {
    private String id;

    public static TripId from(UUID id) {
        return new TripId(id.toString());
    }

    public static TripId from(String id) {
        return new TripId(id);
    }
}
