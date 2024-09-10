package rzepiszczak.damian.tripmaker.trip.management.domain.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

import java.time.LocalDate;

public record TripCreated(String tripId, LocalDate from, LocalDate to) implements DomainEvent {
}
