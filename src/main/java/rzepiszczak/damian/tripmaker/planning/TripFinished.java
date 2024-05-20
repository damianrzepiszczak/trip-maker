package rzepiszczak.damian.tripmaker.planning;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;

public record TripFinished(TripId tripId) implements DomainEvent {
}
