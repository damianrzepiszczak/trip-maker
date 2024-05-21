package rzepiszczak.damian.tripmaker.trip.model.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.model.TripId;

public record TripFinished(TripId tripId) implements DomainEvent {
}
