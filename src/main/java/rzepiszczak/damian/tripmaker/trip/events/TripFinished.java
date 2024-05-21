package rzepiszczak.damian.tripmaker.trip.events;

import rzepiszczak.damian.tripmaker.common.event.DomainEvent;
import rzepiszczak.damian.tripmaker.trip.TripId;

public record TripFinished(TripId tripId) implements DomainEvent {
}
