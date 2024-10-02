package rzepiszczak.damian.tripmaker.trip.domain.management.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;

public record TimelineCreated(TripId tripId) implements DomainEvent {
}
