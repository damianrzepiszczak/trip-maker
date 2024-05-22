package rzepiszczak.damian.tripmaker.traveler;

import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class TravelerId {
    private final String id = UUID.randomUUID().toString();
}
