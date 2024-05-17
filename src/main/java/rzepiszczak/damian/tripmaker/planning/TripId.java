package rzepiszczak.damian.tripmaker.planning;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TripId {
    private final String id = UUID.randomUUID().toString();
}
