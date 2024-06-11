package rzepiszczak.damian.tripmaker.trip.management.application.domain.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.application.domain.TripId;

public record TripFinished(TripId tripId) implements DomainEvent {
}
