package rzepiszczak.damian.tripmaker.trip.domain.management.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.domain.management.model.TripId;

public record TripCanceled(TripId tripId) implements DomainEvent {
}
