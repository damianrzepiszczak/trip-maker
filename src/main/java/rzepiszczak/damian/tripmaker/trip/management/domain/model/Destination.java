package rzepiszczak.damian.tripmaker.trip.management.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Destination {
    private String name;
    public static Destination of(String destination) {
        Objects.requireNonNull(destination, "Destination cannot be null");
        return new Destination(destination);
    }
}
