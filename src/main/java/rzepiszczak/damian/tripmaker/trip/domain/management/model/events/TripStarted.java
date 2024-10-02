package rzepiszczak.damian.tripmaker.trip.domain.management.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;

public record TripStarted(TripId tripId) implements DomainEvent {
}
