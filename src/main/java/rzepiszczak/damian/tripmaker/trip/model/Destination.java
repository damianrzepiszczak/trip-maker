package rzepiszczak.damian.tripmaker.trip.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Destination {
    private String destination;
    static Destination of(String destination) {
        return new Destination(destination);
    }
}
