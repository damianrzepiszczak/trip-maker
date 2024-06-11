package rzepiszczak.damian.tripmaker.trip.management.application.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
class Destination {
    private String destination;
    static Destination of(String destination) {
        return new Destination(destination);
    }
}
