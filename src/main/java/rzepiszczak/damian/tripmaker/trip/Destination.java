package rzepiszczak.damian.tripmaker.trip;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class Destination {

    private String destination;

    static Destination of(String destination) {
        return new Destination(destination);
    }
}
