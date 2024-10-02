package rzepiszczak.damian.tripmaker.trip.domain.management.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelerId {

    private String id;

    public static TravelerId from(UUID id) {
        Objects.requireNonNull(id, "traveler id is required");
        return new TravelerId(id.toString());
    }

    public static TravelerId from(String id) {
        Objects.requireNonNull(id, "traveler id is required");
        return new TravelerId(id);
    }
}
