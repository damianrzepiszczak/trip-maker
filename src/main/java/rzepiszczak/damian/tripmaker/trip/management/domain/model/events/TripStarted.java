package rzepiszczak.damian.tripmaker.trip.management.domain.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.domain.model.TripId;

public record TripStarted(TripId tripId) implements DomainEvent {
}
