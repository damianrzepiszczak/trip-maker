package rzepiszczak.damian.tripmaker.trip.management.application.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Destination {
    private String name;
    static Destination of(String destination) {
        Objects.requireNonNull(destination, "Destination cannot be null");
        return new Destination(destination);
    }
}
