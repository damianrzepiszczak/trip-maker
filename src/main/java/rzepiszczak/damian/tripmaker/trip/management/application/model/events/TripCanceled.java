package rzepiszczak.damian.tripmaker.trip.management.application.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.management.application.model.TripId;

public record TripCanceled(TripId tripId) implements DomainEvent {
}
