package rzepiszczak.damian.tripmaker.trip.hints.application.domain;

import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

import java.util.Optional;

public interface Assistants {
    void save(Assistant assistant);
    Optional<Assistant> findTripAssistant(TripId tripId);
}
