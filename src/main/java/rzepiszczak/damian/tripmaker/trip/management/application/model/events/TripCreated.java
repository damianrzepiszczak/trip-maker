package rzepiszczak.damian.tripmaker.trip.management.application.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

import java.time.LocalDate;

public record TripCreated(String tripId, LocalDate from, LocalDate to) implements DomainEvent {
}
