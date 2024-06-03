package rzepiszczak.damian.tripmaker.trip.management.application.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;

public record TripFinished(TripId tripId) implements DomainEvent {
}
