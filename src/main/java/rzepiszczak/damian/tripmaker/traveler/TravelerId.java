package rzepiszczak.damian.tripmaker.traveler;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TravelerId {
    private final String id = UUID.randomUUID().toString();
}
