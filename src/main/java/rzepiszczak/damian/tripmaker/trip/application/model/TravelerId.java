package rzepiszczak.damian.tripmaker.trip.application.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TravelerId {
    private String id;
    static TravelerId from(UUID id) {
        return new TravelerId(id.toString());
    }
}
